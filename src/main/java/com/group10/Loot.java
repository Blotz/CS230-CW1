package com.group10;

public abstract class Loot extends PickUp{

    private String gemName;

    public Loot(String gem){
        gemName = gem;
    }

    @Override
    public void onInteract(Player p, Level lvl, int currX, int currY) {
        Loot loot = (Loot) lvl.getEntity(currX, currY);

        if (p.equals(true)) {
            if (loot.getGemName() == "Cent") {
                p.setScore(p.getScore() + 5);
            }
            else if (loot.getGemName() == "Dollar"){
                p.setScore(p.getScore() + 10);
            }
            else if (loot.getGemName() == "Ruby"){
                p.setScore(p.getScore() + 15);
            }
            else if (loot.getGemName() == "Diamond"){
                p.setScore(p.getScore() + 20);
            }
            else {
                System.out.println("This is an invalid loot name");
            }
        }
        else {
            // GET RID OF OBJECT //
        }
    }

//    public void lootInteract(Loot loot, Player player) {
//        if (loot.getGemName() == "Cent") {
//            player.setScore(player.getScore() + 5);
//        }
//        else if (loot.getGemName() == "Dollar"){
//            player.setScore(player.getScore() + 10);
//        }
//        else if (loot.getGemName() == "Ruby"){
//            player.setScore(player.getScore() + 15);
//        }
//        else if (loot.getGemName() == "Diamond"){
//            player.setScore(player.getScore() + 20);
//        }
//        else {
//            System.out.println("This is an invalid loot name");
//        }
//    }

    public String getGemName() {
        return gemName;
    }
}