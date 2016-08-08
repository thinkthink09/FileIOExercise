package DataHandles;

public class RandomItem{
    public String name;
    public double probability;
    public RandomItem(){ }
    public RandomItem(String t, double p){
        name = t;
        probability = p;
    }

    public String name() { return name; }
    public double probability() { return probability; }
}