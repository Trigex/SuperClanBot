package moe.trigex.superclanbot.superclanrpg;

import moe.trigex.superclanbot.superclanrpg.weapons.CommonSword;
import moe.trigex.superclanbot.superclanrpg.weapons.GigaDrill;
import moe.trigex.superclanbot.superclanrpg.weapons.ShiningFingers;
import moe.trigex.superclanbot.utils.GatchaShit;
import org.pircbotx.User;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private final int MIN_START_HP = 100;
    private final int MAX_START_HP = 180;

    private User User;
    private int Health;
    private int CurrentHealth;
    private int Level;
    private int Adds;
    private int Money;
    private int Xp;
    private ArrayList<Weapon> Weapons;
    private Weapon DefaultWeapon;
    private boolean Dead;

    public Player(User user) {
        User = user;
        Money = 50;
        Xp = 0;
        Level = 1;
        // Random starting health
        Health = GatchaShit.RollRange(MIN_START_HP, MAX_START_HP);
        // Round Health to nearest 10
        Health = ((Health+5)/10)*10;
        CurrentHealth = Health;
        Weapons = new ArrayList<Weapon>();
        // All players start with common sword
        Weapons.add(new CommonSword());
        Weapons.add(new GigaDrill());
        Weapons.add(new ShiningFingers());
        DefaultWeapon = Weapons.get(0);
        UpdateAdds();
    }

    public void LevelUp() {
        Level++;
        Health += Level*10;
        CurrentHealth+=Health-CurrentHealth;
        Xp = 0;
        UpdateAdds();
    }

    public void AddXp(int count) {
        Xp+=count;
        // If Xp meets current level up target
        if(Xp >= (Level*200)/10) {
            LevelUp();
        }
    }

    public void TakeDamage(int damage) {
        CurrentHealth -= damage;
        UpdateAdds();
    }

    private void UpdateAdds() {
        // Adds are based upon the player's level and current health (If a player is in better
        // condition, logic goes they'd fight better!), and level representing skill.
        Adds = (Level + CurrentHealth)/10;
    }

    public User getUser() {
        return User;
    }
    public Weapon[] getWeapons() {
        Weapon[] weapons = new Weapon[Weapons.size()];
        weapons = Weapons.toArray(weapons);
        return weapons;
    }

    public Weapon getWeapon(String id) {
        Weapon selWeapon = null;
        for(Weapon w : Weapons) {
            if(w.getIdName().equals(id)) {
                selWeapon = w;
            }
        }
        return selWeapon;
    }

    public int getXp() {
        return Xp;
    }
    public int getMoney() {
        return Money;
    }
    public int getHealth() {
        return Health;
    }
    public int getLevel() {
        return Level;
    }
    public int getCurrentHealth() {
        return CurrentHealth;
    }
    public int getAdds() {
        return Adds;
    }

    public Weapon getDefaultWeapon() {
        return DefaultWeapon;
    }
}
