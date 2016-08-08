import java.io.*;

public class DataHandler {
    public static void main(String args[]){
        
    }
    private int counter = 0;
    private String kingdom = "";
    
    
    
    
    
    /**
     *A Simple method to Check if the specified name exists.
     * @param family the target family which holds the name.
     * @param name the specified name.
     * @return true if name exists, false if not.
     */
    private boolean nameExists(String family, String name){
        File f = new File(kingdom+"/"+family+"/"+name);
        return f.exists();
    }
    
    /**
     *A Simple method to Check if the family exists.
     * @param family the target family which holds the name.
     * @return true if name exists, false if not.
     */
    private boolean familyExists(String family){
        File f = new File(kingdom+"/"+family);
        return f.exists();
    }
    
    /**
     * A Simple Method to get knowledge from under a certain name
     * @param family the family which holds the name
     * @param name the specified name
     * @param num the knowledge number under the name of retrun
     * @return single knowledge of the specified name
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private String learnKnowledge(String family, String name, int num) throws FileNotFoundException, IOException{
        FileReader fileReader = new FileReader(kingdom+"/"+family+"/"+name+".txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] text = bufferedReader.readLine().split(",");
        bufferedReader.close();
        return text[num];
    }
    
    /**
     * A Simple Method to modify an existing knowledge under a certain name
     * @param family the family which holds the name
     * @param name the specified name
     * @param num the knowledge number under the name of modification
     * @param value the new content of the target knowledge
     * @return true if successed, false if not
     */
    private boolean obligeKnowledge(String family, String name, int num, String value){
        if(!nameExists(family,name)){
            return false;
        }    
        try{    
            FileReader fileReader = new FileReader(kingdom+"/"+family+"/"+name+".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] text = bufferedReader.readLine().split(",");
            bufferedReader.close();
            text[num] = value;
            StringBuilder builder = new StringBuilder();
            for(String s : text){
                builder.append(s);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length()-1);
            FileWriter fileWriter = new FileWriter(kingdom+"/"+family+"/"+name+".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(builder.toString());
            bufferedWriter.close();
            return true;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * A Simple Method to insert a new knowledge of specified values into a certain name
     * @param family the family which holds the name
     * @param name the specified name
     * @param num the knowledge number of the new knowledge, rest of the old knowledges will be appended at back
     * @param value the new value of the target knowledge
     * @return true if successed, false if not
     */
    private boolean teachKnowledge(String family, String name, int num, String value){
        if(!nameExists(family,name)){
            return false;
        }    
        try{    
            FileReader fileReader = new FileReader(kingdom+"/"+family+"/"+name+".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] text = bufferedReader.readLine().split(",");
            bufferedReader.close();
            
            StringBuilder builder = new StringBuilder();
            int count=0;
            for(int i = 0; i<text.length+1;i++){
                if(i == num){
                builder.append(value);
                builder.append(",");
                }else{
                builder.append(text[count++]);
                builder.append(",");
                }
            }
            builder.deleteCharAt(builder.length()-1);
            FileWriter fileWriter = new FileWriter(kingdom+"/"+family+"/"+name+".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(builder.toString());
            bufferedWriter.close();
            return true;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Another Method to Create a name under certain family with specified Knowledges
     * @param family specifies the family of adding the name
     * @param name the name of the new name
     * @param Knowledges Knowledge(s)to append under the newly created name
     * @return true if name created, false if not
     */
    private boolean giveBirth(String family, String name, String knowledges[]){
        if(familyExists(family)){
            return false;
        }
        File f = new File(kingdom+"/"+family+"/"+name+".txt");
        try{
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder builder = new StringBuilder();
            for(String knowledge : knowledges){
                builder.append(knowledge);
                builder.append(",");
            }
            builder.deleteCharAt(builder.length()-1);
            bufferedWriter.write(builder.toString());
            bufferedWriter.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * A Simple Method to Create a name under certain family
     * @param family specifies the family of adding the name
     * @param name the name of the new name(XD)
     * @return true if name created, false if not
     */
    private boolean giveBirth(String family, String name){
        if(familyExists(kingdom+"/"+family)){
            return false;
        }
        File f = new File("Fate/"+family+"/"+name+".txt");
        try{
            FileWriter fileWriter = new FileWriter(f);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("");
            bufferedWriter.close();
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * A Simple Method to create a new family
     * @param family name of the family
     * @return true if creation is successful, false if not
     */
    private boolean buildHouse(String family){
        File f = new File(kingdom+"/"+family);
        return f.mkdir();
    }
}
