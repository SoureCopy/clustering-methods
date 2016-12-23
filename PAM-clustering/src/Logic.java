import java.util.ArrayList;

public class Logic {

    MatrixDistance matr;
    int amountDepot;
    int amountClient;
    ArrayList<Depot> depots = new ArrayList<Depot>();

    public void test() {
            matr = new MatrixDistance();
            matr.matrix = new ObjectMatrix[20][16];
            amountClient=16;
            amountDepot=4;

            for (int i = 0; i < amountDepot; i++) {
                Depot d1 = new Depot();
                d1.id = i;
                d1.startWindow = (int)(Math.random()*1438);
                d1.isDepot=true;
                int endWindow = (int)(Math.random()*1439);
                while (endWindow<=d1.startWindow) {
                    endWindow = (int)(Math.random()*1439);
                }
                d1.endWindow = endWindow;
                depots.add(d1);
            }
            for (int i = 0; i < amountClient; i++) {
                Client client = new Client();
                client.id = i;
                client.startWindow = (int)(Math.random()*1438);
                int endWindow = (int)(Math.random()*1439);
                while (endWindow<=client.startWindow) {
                    endWindow = (int)(Math.random()*1439);
                }
                client.endWindow = endWindow;
                matr.clients.add(client);
            }
            for (int i = 0; i < matr.matrix.length; i++) {
                if (i==0){
                    System.out.println("-----------------depots-------------------");
                }
                for (int j = 0; j < matr.matrix[i].length; j++) {
                    ObjectMatrix objM;
                    if (i < amountDepot) {
                        objM = new ObjectMatrix(depots.get(i), matr.clients.get(j), (int) (Math.random() * 15 + 1));
                    }
                    else if (i-amountDepot==j){
                        objM = new ObjectMatrix(matr.clients.get(i-amountDepot), matr.clients.get(j), 0);
                    }
                    else if (i-amountDepot<j){
                        objM = new ObjectMatrix(matr.clients.get(i-amountDepot), matr.clients.get(j), (int) (Math.random() * 15 + 1));
                    }
                    else {
                        objM = new ObjectMatrix(matr.clients.get(i-amountDepot), matr.clients.get(j), matr.matrix[j+amountDepot][i-amountDepot].dist);
                    }
                    matr.matrix[i][j] = objM;
                    System.out.print(" " + matr.matrix[i][j].dist);
                }
                System.out.println();
                if (i==amountDepot-1){
                    System.out.println("-----------------clients------------------");
                }
            }
        }

    public void pamCluster(){
        for (int i=0; i<matr.matrix[0].length; i++){
            for(int j=0; j<matr.matrix.length; j++) {
                matr.matrix[j][i].dist = matr.matrix[j][i].client.getPamDistance(matr,i,j);
            }
        }
        for (int i=0; i<matr.clients.size(); i++){
            for(int j=0; j<matr.matrix[0].length; j++){
                if (matr.matrix[0][j].client.id == matr.clients.get(i).id){
                    depots.get(findBestDepot(j)).cluster.add(matr.clients.get(j));
                }
            }
        }
    }

    private int findBestDepot(int j){
        double record=0.0;
        double temp=0.0;
        int numbDepot=-1;
        for(int k=0; k<depots.size(); k++){
            for(int i=0; i<matr.matrix.length; i++){
                if((depots.get(k).id==matr.matrix[i][j].point.id)&&(matr.matrix[i][j].point.isDepot)){
                    temp = matr.matrix[i][j].dist;
                    break;
                }
            }
            for(int l=0; l<depots.get(k).cluster.size(); l++){
                for(int i=0; i<matr.matrix.length; i++){
                    if ((depots.get(k).cluster.get(l).id==matr.matrix[i][j].point.id)&&(!matr.matrix[i][j].point.isDepot)) {
                        temp += matr.matrix[i][j].dist;
                        break;
                    }
                }
            }
            temp/=(depots.get(k).cluster.size()+1);
            temp = findMedoid(temp, j, k);
            if ((temp<record)||(record==0.0)){
                record=temp;
                numbDepot=k;
            }
        }
        return numbDepot;
    }

    private double findMedoid(double temp, int j, int k){
        double eps=-1.0;
        double medoid=-1.0;
        for(int i=0; i<matr.matrix.length; i++){
            if((depots.get(k).id==matr.matrix[i][j].point.id)&&(matr.matrix[i][j].point.isDepot)){
                eps=Math.abs(temp-matr.matrix[i][j].dist);
                medoid=matr.matrix[i][j].dist;
                break;
            }
        }
        for(int l=0; l<depots.get(k).cluster.size(); l++){
            for(int i=0; i<matr.matrix.length; i++){
                if ((depots.get(k).cluster.get(l).id==matr.matrix[i][j].point.id)&&(!matr.matrix[i][j].point.isDepot)) {
                    if(Math.abs(matr.matrix[i][j].dist-temp)<eps){
                        eps=Math.abs(matr.matrix[i][j].dist-temp);
                        medoid=matr.matrix[i][j].dist;
                    }
                    break;
                }
            }
        }
        return medoid;
    }

    public void showCluster(){
        for(int i=0; i<depots.size(); i++){
            System.out.print(depots.get(i)+" --- ");
            for(int k=0; k<depots.get(i).cluster.size(); k++){
                System.out.print(depots.get(i).cluster.get(k)+" ");
            }
            System.out.println();
        }
    }
}
