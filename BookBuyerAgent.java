package Books.agents;

import Books.gui.BookBuyerGui;
import jade.core.Agent;
import Books.behaviours.RequestPerformer;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BookBuyerAgent extends Agent {
  private String bookTitle;
  private AID[] sellerAgents;
  private int ticker_timer = 10000;
  private BookBuyerAgent this_agent = this;
  private BookBuyerGui gui;
  private Object libro;
  private boolean visibleGUI = false;
  int contador = 0;
  
  
  protected void setup() {
    System.out.println("Buyer agent " + getAID().getName() + " is ready");
    
    //dentro de setup crear la gui
    if(visibleGUI == false){
        gui = new BookBuyerGui(this,1,"",1);
        gui.showGui();
        visibleGUI =true;
    }
    
    Object[] args = getArguments();
    if(args != null && args.length > 0) {
      bookTitle = (String)args[0];
      System.out.println("Book: " + bookTitle);
      
      addBehaviour(new TickerBehaviour(this, ticker_timer) {
        protected void onTick() {
          System.out.println("Trying to buy " + bookTitle);
          
          gui.dispose();
          if(contador<10){
              gui = new BookBuyerGui(this_agent, 2, "", 1);
              gui.showGui();
              contador++;
          }
          if(contador >=10){
              gui = new BookBuyerGui(this_agent, 3, "", 1);
              gui.showGui();
          }
          
          DFAgentDescription template = new DFAgentDescription();
          ServiceDescription sd = new ServiceDescription();
          sd.setType("book-selling");
          template.addServices(sd);
          
          try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            System.out.println("Found the following seller agents:");
            sellerAgents = new AID[result.length];
            for(int i = 0; i < result.length; i++) {
              sellerAgents[i] = result[i].getName();
              System.out.println(sellerAgents[i].getName());
            }
            
          }catch(FIPAException fe) {
            fe.printStackTrace();
          }
          
          myAgent.addBehaviour(new RequestPerformer(this_agent));
        }
      });
    } else {
      System.out.println("No target book title specified");
      //doDelete();
    }
  }
  
  protected void takeDown() {
    System.out.println("Buyer agent " + getAID().getName() + " terminating");
  }
  
  public AID[] getSellerAgents() {
    return sellerAgents;
  }
  
  public String getBookTitle() {
    return bookTitle;
  }
  
  public Object buscarLibro(Object lib){
      libro = lib;
      Object [] cad = new String[1];
      cad[0] = lib;
      setArguments(cad);
      setup();
      gui.dispose();
      return libro;
         
  }

    public int getTicker_timer() {
        return ticker_timer;
    }

    public void setTicker_timer(int ticker_timer) {
        this.ticker_timer = ticker_timer;
    }

    public BookBuyerAgent getThis_agent() {
        return this_agent;
    }

    public void setThis_agent(BookBuyerAgent this_agent) {
        this.this_agent = this_agent;
    }

    public BookBuyerGui getGui() {
        return gui;
    }

    public void setGui(BookBuyerGui gui) {
        this.gui = gui;
    }

    public Object getLibro() {
        return libro;
    }

    public void setLibro(Object libro) {
        this.libro = libro;
    }

    public boolean isVisibleGUI() {
        return visibleGUI;
    }

    public void setVisibleGUI(boolean visibleGUI) {
        this.visibleGUI = visibleGUI;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
  
  
}
