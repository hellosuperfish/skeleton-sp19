public class PrintStarsII {
   public static void main(String[] args) {
     PrintStars ps = new PrintStars();
     ps.DrawTrangle(10);
     
   }
   public void DrawTrangle(int n){
    for (int i=0; i<n; i++){
         for(int j=0; j<=i; j++){
         System.out.print('*');
      }
         System.out.print('\n');
      }
   }
}