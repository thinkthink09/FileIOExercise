package DataHandles;


public class Actions {
    public static void main(String[] args){
        CraftItem("gnome","logging",1);
    }
    public static boolean CraftItem(String name, String itemname, int itemlevel){
        int money = Integer.parseInt(Data.getData(name, 4));
        int kingdom = Integer.parseInt(Data.getKingdomData(name, 3));
        switch(itemlevel){
            case 0:
                money-=5000;
                kingdom+=5000;
                break;
            case 1:
                money-=10000;
                kingdom+=10000;
                break;
            case 2:
                money-=20000;
                kingdom+=20000;
                break;
            case 3:
                money-=50000;
                kingdom+=50000;
                break;
            default:
        }
        if(money<0){
            return false;
        }
        int playermaterials[] = Data.getMaterialList(name);
        int materialcost[] = Data.getCraftingMaterial(itemname, itemlevel);
        for(int i= 0;i<playermaterials.length;i++){
            playermaterials[i]-=materialcost[i];
            if(playermaterials[i]<0){
                return false;
            }
        }
        Data.setKingdomData(name, 3, kingdom+"");
        Data.setData(name, 4, money+"");
        Data.setMaterialList(name, playermaterials);
        Data.addItem(name, itemname, itemlevel);
        return true;
    }
    public static void GotMaterial(String name, int materialnum){
        int value = Integer.parseInt(Data.getMaterial(name, materialnum))+1;
        Data.setMaterial(name, materialnum, value+"");
    }
    public static int[] DealMaterials(String name, int[] newmaterials){
        int oldmaterials[] = Data.getMaterialList(name);
        int shopmaterials[] = Data.getStoreMaterialList(name);
        int[] diff = new int[9];
        for(int i = 0;i<diff.length;i++){
            diff[i] = newmaterials[i]-oldmaterials[i];
        }
        int payment = 0;
        // diff>0 = buy
        // payment = player pays
        if(diff[0]>0){
                payment+=(diff[0]*80);
        }else if(diff[0]<0){
                payment+=(diff[0]*20);
        }
        if(diff[1]>0){
                payment+=(diff[1]*20);
        }else if(diff[1]<0){
                payment+=(diff[1]*5);
        }
        if(diff[2]>0){
                payment+=(diff[2]*250);
        }else if(diff[2]<0){
                payment+=(diff[2]*50);
        }
        if(diff[3]>0){
                payment+=(diff[3]*125);
        }else if(diff[3]<0){
                payment+=(diff[3]*25);
        }
        if(diff[4]>0){
                payment+=(diff[4]*30);
        }else if(diff[4]<0){
                payment+=(diff[4]*10);
        }
        if(diff[5]>0){
                payment+=(diff[5]*200);
        }else if(diff[5]<0){
                payment+=(diff[5]*40);
        }
        if(diff[6]>0){
                payment+=(diff[6]*40);
        }else if(diff[6]<0){
                payment+=(diff[6]*10);
        }
        if(diff[7]>0){
                payment+=(diff[7]*250);
        }else if(diff[7]<0){
                payment+=(diff[7]*50);
        }
        if(diff[8]>0){
                payment+=(diff[8]*10500);
        }else if(diff[8]<0){
                payment+=(diff[8]*10000);
        }
        
        int money = Integer.parseInt(Data.getData(name, 4));
        for(int i = 0;i<diff.length;i++){
            shopmaterials[i]-=diff[i];
            if(shopmaterials[i]<0){
                int[] ret = {0, 0};
                return ret;
            }
        }
        if((money-payment)>=0){
            Data.setData(name, 4, (money-payment)+"");
            Data.setMaterialList(name, newmaterials);
            Data.setStoreMaterialList(name, shopmaterials);
            int kingdommoney = Integer.parseInt(Data.getKingdomData(name, 3));
            Data.setKingdomData(name, 3, (kingdommoney+payment)+"");
        }
        int[] ret = {money-payment, payment};
        return ret;
    }
    public static boolean Work(String name, int staminacost, int workcost, int payment, int worktype, int stackup){
        try{
            // add kingdom check if kingdom dosent exist then return false
            int money = Integer.parseInt(Data.getData(name, 4));
            int stamina = Integer.parseInt(Data.getData(name, 6));
            money-=workcost;
            stamina-=staminacost;
            //check if money and stamina is enough
            if(money<0||stamina<0){
                return false;
            }else{
                int fame =  Integer.parseInt(Data.getData(name, 5));
                // worktype: 0-land, 1-army, 2-support, 3-money, 4-food, 5-wood, 6-metal
                int resource = Integer.parseInt(Data.getKingdomData(name, worktype));
                int kingdommoney = Integer.parseInt(Data.getKingdomData(name, 3));
                money+=payment;
                fame+=stackup/5;
                resource+=stackup;
                kingdommoney+=workcost;
                kingdommoney-=payment;
                Data.setData(name, 4, money+"");
                Data.setData(name, 5, fame+"");
                Data.setData(name, 6, stamina+"");
                Data.setKingdomData(name, worktype, resource+"");
                Data.setKingdomData(name, 3, kingdommoney+"");
                return true;
            }
        }catch(NumberFormatException e){
            return false;
        }
    }
    public static int PayTax(String name, int value){
        int money = Integer.parseInt(Data.getData(name, 4));
        if(money>=value){
            money-=value;
        }else{
            value = money;
            money = 0;
        }
        
        int kingdom = Integer.parseInt(Data.getKingdomData(name, 3));
        kingdom+=value;
        
        Data.setData(name, 4, money+"");
        Data.setKingdomData(name, 3, kingdom+"");
        return value;
    }
    public static int BuyFood(String name, int value){
        int money = Integer.parseInt(Data.getData(name, 4));
        int kingdom = Integer.parseInt(Data.getKingdomData(name, 3));
        int food = Integer.parseInt(Data.getKingdomData(name, 4));
        if(money>=value){
            money-=value;
            Data.setData(name, 4, money+"");
        }else{
            int stamina = Integer.parseInt(Data.getData(name, 6));
            stamina-=value/100;
            Data.setData(name, 6, stamina+"");
        }
        kingdom+=value;
        food-=value;
        Data.setKingdomData(name, 3, kingdom+"");
        Data.setKingdomData(name, 4, food+"");
        return value;
    }
}
