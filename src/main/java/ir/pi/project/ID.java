package ir.pi.project;

public class ID {

    public static int getIdFromFileName(String fileName){
        for(int i=0;i<5;i++){
            fileName=fileName.substring(0,fileName.length()-1);
        }
        int q=Integer.parseInt(fileName);
        return q;
    }
}
