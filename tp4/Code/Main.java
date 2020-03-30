import java.util.*; 


public class Main 
{
   /**
     * Fonction principale
     */
   public static void main(String[] args) 
   {
      // Creer un monceau avec 22 éléments et un tableau équivalent
      int numItems = 22;
      BinaryHeap<Integer> heap = new BinaryHeap<Integer>(true);
      
      Integer [ ] items = new Integer[ numItems ];

      int i;
      int j;

      // En insérant les éléments un a un
      for( i = 11, j = 0; j != numItems; i = ( i + 37 ), j++ )
      {
         heap.offer( i );
         items[ j ] = i;

         i %=  numItems;
      }

      // en construisant le monceau depuis le depart
      System.out.println("Monceau min contruit element par element:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(false);
      // en inserant les elements un a un
      for( Integer item : items)
	  heap.offer( item );

      // en construisant le monceau depuis le depart
      System.out.println("Monceau max contruit element par element:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(items,false);
      System.out.println("Monceau max contruit a partir d'un tableau:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(items,true);
      System.out.println("Monceau min contruit a partir d'un tableau:");
      System.out.println( heap.printFancyTree() );

      System.out.println();
      System.out.println("Affichage recursif:");
      System.out.println( heap.printFancyTree() );

      System.out.println("Affichage non recursif:");
      System.out.println( heap.nonRecursivePrintFancyTree() );


      System.out.println();
      System.out.println("Tableau d'origine:");
      System.out.println( printArray( items ) );
      
      BinaryHeap.heapSort( items );
      System.out.println("Tableau ordonne:");
      System.out.println( printArray( items ) );

      BinaryHeap.heapSortReverse( items );
      System.out.println("Tableau inversement ordonne:");
      System.out.println( printArray( items ) );
      
      /*
       * Ajouter appels pour repondre a la question
       **/

      System.out.println("\nPartie 3 \n");

      PriorityQueue<Integer> prioQ = new PriorityQueue<Integer>();
      prioQ.addAll(Arrays.asList(items));

      System.out.println("Test poll sur PriorityQueue");
      System.out.println(  "Resultat du poll : "+prioQ.poll() );
      System.out.println("Test poll sur BinaryHeap");
      System.out.println( "Resultat du poll : " + heap.poll() );

      System.out.println( "Test modification pendant iteration sur PriorityQueue : Pas d'exception malgré modification durant l'iteration " );
      for (int item : prioQ) {
         heap.offer(8); // pas implementer ici donc iteration devrait continuer malgré modification
      }System.out.println( "Iteration terminée" );
      System.out.println( "Test modification pendant iteration sur BinaryHeap : Exception demandée et reussite car modification durant l'iteration" );
      for (int item : heap) {
         heap.offer(8);//modification devrait induire erreur ConcurrentModificationException, fonctionne
      }System.out.println( "Iteration terminée" );

   }

   private static <AnyType> String printArray(AnyType[] a)
   {
      String outputString = "";

      for(AnyType item : a)
      {
         outputString += item;
         outputString += ", ";
      }

      return outputString.substring(0,outputString.length()-2);
   }
}
