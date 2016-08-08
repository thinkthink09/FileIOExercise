package DataHandles;


import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Threads {
    public static void main(String[] args){
        StaminaRefill();
    }
    public static void StaminaRefill(){
        File folder = new File("SaveData/Accounts");
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            //if (listOfFile.isFile())
            String name = listOfFile.getName();
            if(Data.getData(name, 3).equals("chopper")){
                int stamina = Integer.parseInt(Data.getData(name, 6));
                if(stamina<9){
                    Data.setData(name, 6, (++stamina)+"");
                }
            }else{
                int stamina = Integer.parseInt(Data.getData(name, 6));
                if(stamina<8){
                    Data.setData(name, 6, (++stamina)+"");
                }
            }
        }
    }
    public static void DayBegin(){
        File folder = new File("SaveData/Accounts");
        File[] listOfFiles = folder.listFiles();

        for (File listOfFile : listOfFiles) {
            String name = listOfFile.getName();
            if(Data.getData(name, 3).equals("chopper")){
                Data.setData(name, 6, "9");
            }else{
                Data.setData(name, 6, "8");
            }
            Data.setData(name, 7, "0");
        }
    }
    public static void NewsGenerator(){
        StringBuilder generatednews = new StringBuilder();
        Calendar today = new GregorianCalendar();
        generatednews.append("Created on server time: ");
        generatednews.append(today.getTime().toString());
        generatednews.append("<br />");
        
        RandomItem[] news = new RandomItem[]{
            new RandomItem("fg",0.05),
            new RandomItem("fb",0.06),
            new RandomItem("cg",0.05),
            new RandomItem("cb",0.06),
            new RandomItem("mg",0.05),
            new RandomItem("mb",0.06),
        };
        
        
        switch(Data.RandomSelector(news).name){
            case "fg":
                generatednews.append("Today is good for farming.");
                generatednews.append("");
                break;
            case "cg":
                generatednews.append("Today is good for chopping.");
                generatednews.append("");
                break;
            case "mg":
                generatednews.append("Today is good for mining.");
                generatednews.append("");
                break;
            case "fb":
                generatednews.append("Today is bad for farming.");
                generatednews.append("");
                break;
            case "cb":
                generatednews.append("Today is bad for chopping.");
                generatednews.append("");
                break;
            case "mb":
                generatednews.append("Today is bad for mining.");
                generatednews.append("");
                break;
            default:
                generatednews.append("No news today.");
                generatednews.append("");
        }
        try {
            FileWriter fileWriter = new FileWriter("SaveData/News.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(generatednews.toString());
            bufferedWriter.close();
        }catch(IOException e) {
        }
    }
    
    
}
