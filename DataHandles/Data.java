package DataHandles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;

public class Data {
    public static void main(String[] args){
        int[] costlist = Data.getCraftingMaterial("logging", -1);
        for(int cost : costlist){
            System.out.print(cost+", ");
        }
    }
    
    public static RandomItem RandomSelector(RandomItem[] items) {
        double totalProbability = 0.0;
        for (RandomItem item : items) {
            totalProbability += item.probability;
        }
        RandomItem[] fixeditems = new RandomItem[items.length+1];
        System.arraycopy(items, 0, fixeditems, 1, items.length);
        fixeditems[0]=new RandomItem("",1-totalProbability);
        
        double p = Math.random();
        double cumulativeProbability = 0.0;
        
        for (RandomItem item : fixeditems) {
            cumulativeProbability += item.probability;
            if (p <= cumulativeProbability) {
                return item;
            }
        }
        return fixeditems[fixeditems.length];
        
    }
    public static void setKingdomData(String name, int datanum, String value){
        File f = new File("SaveData/Kingdoms/"+name+"/kingdom.txt");
        if(f.exists()) {
            try {
                FileReader fileReader = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String[] text = bufferedReader.readLine().split(",");
                bufferedReader.close();
                text[datanum] = value;
                StringBuilder builder = new StringBuilder();
                for(String s : text){
                    builder.append(s);
                    builder.append(",");
                }
                builder.deleteCharAt(builder.length()-1);
                FileWriter fileWriter = new FileWriter(f);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(builder.toString());
                bufferedWriter.close();
            }catch(IOException e) {
            }
        }else{
            f = new File("SaveData/Kingdoms/"+getData(name,1)+"/kingdom.txt");
            try {
                FileReader fileReader = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String[] kingdom = bufferedReader.readLine().split(",");
                bufferedReader.close();
                kingdom[datanum] = value;
                StringBuilder builder = new StringBuilder();
                for(String s : kingdom){
                    builder.append(s);
                    builder.append(",");
                }
                builder.deleteCharAt(builder.length()-1);
                FileWriter fileWriter = new FileWriter(f);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(builder.toString());
                bufferedWriter.close();
            }catch(FileNotFoundException e){
            }
            catch(IOException e) {
            }
        }
    }
    public static String getKingdomData(String name, int datanum){
        File f = new File("SaveData/Kingdoms/"+name+"/kingdom.txt");
        if(f.exists()) {
            try {
                FileReader fileReader = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String[] text = bufferedReader.readLine().split(",");
                bufferedReader.close();
                return text[datanum];
            }catch(IOException e) {
                return "";
            }
        }else{
            try {
                FileReader fileReader = new FileReader("SaveData/Kingdoms/"+getData(name,1)+"/kingdom.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String[] kingdom = bufferedReader.readLine().split(",");
                return kingdom[datanum];
            }catch(FileNotFoundException e){
                return "";
            }
            catch(IOException e) {
                return "";
            }
        }
    }
    public static void setBonus(String kingdom, float money, float food, float wood, float metal){
        try{
            FileWriter fileWriter = new FileWriter("SaveData/Kingdoms/"+kingdom+"/bonus.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(money+","+food+","+wood+","+metal);
            bufferedWriter.close();
        }catch(IOException e){
        }
    }
    public static String getNews(){
        try {
            FileReader fileReader = new FileReader("SaveData/News.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String news = bufferedReader.readLine();
            bufferedReader.close();
            return news;
        }catch(FileNotFoundException e) {
            return "";
        }catch(IOException e) {
            return "";
        }
    }
    public static String getUpdateLogs(){
        try {
            FileReader fileReader = new FileReader("SaveData/UpdateLog.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder log = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine())!=null){
                log.append("<li>");
                log.append(line);
                log.append("</li>");
            }
            bufferedReader.close();
            return log.toString();
        }catch(FileNotFoundException e) {
            return "";
        }catch(IOException e) {
            return "";
        }
    }
    public static void addItem(String name, String item, int level){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/items.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder items = new StringBuilder();
            String line;
            boolean appended = false;
            while((line = bufferedReader.readLine())!=null){
                if(line.substring(0,line.indexOf(",")).equals(item)){
                    line = item+","+level;
                    appended = true;
                }
                items.append(line);
                items.append(System.getProperty("line.separator"));
            }
            if(!appended){
                line = item+","+level;
                items.append(line);
                items.append(System.getProperty("line.separator"));
            }
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter("SaveData/Accounts/"+name+"/items.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(items.toString());
            bufferedWriter.close();
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
    }
    public static int getItemLevel(String name, String item){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/items.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine())!=null){
                if(line.substring(0,line.indexOf(",")).equals(item)){
                    int level = Integer.parseInt(line.substring(line.indexOf(",")+1));
                    bufferedReader.close();
                    return level;
                }
            }
            bufferedReader.close();
            return -1;
        }catch(FileNotFoundException e) {
            return -1;
        }catch(IOException e) {
            return -1;
        }
    }
    public static String[] getItemList(String name){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/items.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<String> lines = new ArrayList<String>();
            String line;
            while((line = bufferedReader.readLine())!=null){
                lines.add(line);
            }
            bufferedReader.close();
            return lines.toArray(new String[lines.size()]);
        }catch(FileNotFoundException e) {
            return null;
        }catch(IOException e) {
            return null;
        }
    }
    public static int[] getCraftingMaterial(String itemname, int itemlevel){
        try {
            FileReader fileReader = new FileReader("SaveData/Items/"+itemname+"/material.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] lines;
            for(int i = 0;i<itemlevel;i++){
                bufferedReader.readLine();
            }
            lines = bufferedReader.readLine().split(",");
            bufferedReader.close();
            int[] list = new int[9];
            for(int i=0;i<lines.length;i++){
                list[i]=Integer.parseInt(lines[i]);
            }
            return list;
        }catch(FileNotFoundException e) {
            int[] list = {0,0,0,0,0,0,0,0,0};
            return list;
        }catch(IOException e) {
            int[] list = {0,0,0,0,0,0,0,0,0};
            return list;
        }
    }
    public static int[] getMaterialList(String name){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/material.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] lines;
            lines = bufferedReader.readLine().split(",");
            bufferedReader.close();
            int[] list = new int[9];
            for(int i=0;i<lines.length;i++){
                list[i]=Integer.parseInt(lines[i]);
            }
            return list;
        }catch(FileNotFoundException e) {
            return null;
        }catch(IOException e) {
            return null;
        }
    }
    public static int[] getStoreMaterialList(String name){
        try {
            int[] list = new int[9];
            File f = new File("SaveData/Kingdoms/"+name+"/store.txt");
            if(f.exists()) {                
                FileReader fileReader = new FileReader("SaveData/Kingdoms/"+name+"/store.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String[] lines;
                lines = bufferedReader.readLine().split(",");
                bufferedReader.close();
                for(int i=0;i<lines.length;i++){
                    list[i]=Integer.parseInt(lines[i]);
                }
            }
            else{                
                FileReader fileReader = new FileReader("SaveData/Kingdoms/"+getData(name,1)+"/store.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String[] lines;
                lines = bufferedReader.readLine().split(",");
                bufferedReader.close();
                for(int i=0;i<lines.length;i++){
                    list[i]=Integer.parseInt(lines[i]);
                }
            }                        
            return list;
        }catch(FileNotFoundException e) {
            return null;
        }catch(IOException e) {
            return null;
        }
    }
    public static void setStoreMaterialList(String name,int[] materialset){
        try {
            StringBuilder materiallist = new StringBuilder();
            for(int materialnum :materialset){
                materiallist.append(materialnum+",");
            }
            materiallist.deleteCharAt(materiallist.length()-1);
            File f = new File("SaveData/Kingdoms/"+name+"/store.txt");
            if(f.exists()){
                FileWriter fileWriter = new FileWriter("SaveData/Kingdoms/"+name+"/store.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(materiallist.toString());
                bufferedWriter.close();
            }else{
                //kingdom might be playername
                FileWriter fileWriter = new FileWriter("SaveData/Kingdoms/"+getData(name,1)+"/store.txt");
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(materiallist.toString());
                bufferedWriter.close();
            }
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
    }

    public static void setMaterialList(String name,String materiallist){
        try {
            FileWriter fileWriter = new FileWriter("SaveData/Accounts/"+name+"/material.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(materiallist);
            bufferedWriter.close();
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
    }
    public static void setMaterialList(String name,int[] materialset){
        try {
            StringBuilder materiallist = new StringBuilder();
            for(int materialnum :materialset){
                materiallist.append(materialnum+",");
            }
            materiallist.deleteCharAt(materiallist.length()-1);
            FileWriter fileWriter = new FileWriter("SaveData/Accounts/"+name+"/material.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(materiallist.toString());
            bufferedWriter.close();
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
    }
    public static String getMaterial(String name, int datanum){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/material.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] text = bufferedReader.readLine().split(",");
            bufferedReader.close();
            return text[datanum];
        }catch(FileNotFoundException e) {
            return "";
        }catch(IOException e) {
            return "";
        }catch(NullPointerException e){
            return "";
        }
    }
    public static void setMaterial(String name, int datanum, String value){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/material.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] text = bufferedReader.readLine().split(",");
            bufferedReader.close();
            text[datanum] = value;
            StringBuilder builder = new StringBuilder();
            for(String s : text){
                builder.append(s);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length()-1);
            FileWriter fileWriter = new FileWriter("SaveData/Accounts/"+name+"/material.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(builder.toString());
            bufferedWriter.close();
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
    }
    public static String getData(String name, int datanum){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/info.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] text = bufferedReader.readLine().split(",");
            bufferedReader.close();
            return text[datanum];
        }catch(FileNotFoundException e) {
            return "";
        }catch(IOException e) {
            return "";
        }catch(NullPointerException e){
            return "";
        }
    }
    public static boolean NameExists(String name){
        File f = new File("SaveData/Accounts/"+name);
        if(f.exists())return true;
        f = new File("SaveData/Kingdoms/"+name);
        if(f.exists())return true;
        return false;
    }
    public static void setData(String name, int datanum, String value){
        try {
            FileReader fileReader = new FileReader("SaveData/Accounts/"+name+"/info.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] text = bufferedReader.readLine().split(",");
            bufferedReader.close();
            text[datanum] = value;
            StringBuilder builder = new StringBuilder();
            for(String s : text){
                builder.append(s);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length()-1);
            FileWriter fileWriter = new FileWriter("SaveData/Accounts/"+name+"/info.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(builder.toString());
            bufferedWriter.close();
        }catch(FileNotFoundException e) {
        }catch(IOException e) {
        }
    }
    public static String GetCookieByName(Cookie[] cookies,String name){
        
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        return "";
    }
    public static boolean CreateAccount(String name,String pwd,String kingdom,String gender,String job){
        File f = new File("SaveData/Accounts/"+name);
        if(!f.mkdir()) {
            return false;
        }else{
            try{
            FileWriter fileWriter = new FileWriter("SaveData/Accounts/"+name+"/info.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //which means password,kingdom,gender,class,1000 money,0 reputation, 3stamina, has read news or not 
            bufferedWriter.write(pwd+","+kingdom+","+gender+","+job+",2000,0,4,1");
            //initial account material and item data file
            bufferedWriter.close();
            fileWriter = new FileWriter("SaveData/Accounts/"+name+"/items.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
            fileWriter = new FileWriter("SaveData/Accounts/"+name+"/material.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("0,0,0,0,0,0,0,0,0");
            bufferedWriter.close();
            return true;
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
    }
    public static boolean CreateKingdom(String name){
        if(NameExists(name)) {
            return false;
        }else{
            try{
            FileWriter fileWriter = new FileWriter("SaveData/Kingdoms/"+name+"/kingdom.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //which means password,kingdom,gender,class,1000 money,0 reputation, 3stamina, has read news or not 
            bufferedWriter.write("1,100,100,10000,1000,1000,0");
            //initial account material and item data file
            bufferedWriter.close();
            fileWriter = new FileWriter("SaveData/Kingdoms/"+name+"/bonus.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
            fileWriter = new FileWriter("SaveData/Kingdoms/"+name+"/shop.txt");
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("0,0,0,0,0,0,0,0,0");
            bufferedWriter.close();
            return true;
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
    }
}
