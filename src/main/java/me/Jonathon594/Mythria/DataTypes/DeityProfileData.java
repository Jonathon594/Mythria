package me.Jonathon594.Mythria.DataTypes;

import me.Jonathon594.Mythria.Enum.Deity;

public class DeityProfileData {
    public static Integer getDexterity(Deity d) {
        switch (d) {
            case FELIXIA:
                return 80;
            case SELINA:
                return 120;
            case RAIKA:
                return 212;
            case ELIANA:
                return 110;
            case MELINIAS:
                return 75;
            case KASAI:
                return 47;
            case ASANA:
                return 20;
            case LILASIA:
                return 130;
            case MAL:
                return 212;
        }
        return 0;
    }

    public static Integer getEndurance(Deity d) {
        switch (d) {
            case FELIXIA:
                return 100;
            case SELINA:
                return 100;
            case RAIKA:
                return 100;
            case ELIANA:
                return 100;
            case MELINIAS:
                return 400;
            case KASAI:
                return 800;
            case ASANA:
                return 800;
            case LILASIA:
                return 100;
            case MAL:
                return 1280;
        }
        return 0;
    }

    public static Integer getIntelligence(Deity d) {
        switch (d) {
            case FELIXIA:
                return 800;
            case SELINA:
                return 750;
            case RAIKA:
                return 240;
            case ELIANA:
                return 600;
            case MELINIAS:
                return 700;
            case KASAI:
                return 500;
            case ASANA:
                return 350;
            case LILASIA:
                return 780;
            case MAL:
                return 42;
        }
        return 0;
    }

    public static String getLastName(Deity d) {
        switch (d) {
            case FELIXIA:
                return "Light";
            case SELINA:
                return "Life";
            case RAIKA:
                return "Thunder";
            case ELIANA:
                return "Wind";
            case MELINIAS:
                return "Water";
            case KASAI:
                return "Fire";
            case ASANA:
                return "Earth";
            case LILASIA:
                return "Darkness";
            case MAL:
                return "Creation";
        }
        return "";
    }

    public static Integer getStrength(Deity d) {
        switch (d) {
            case FELIXIA:
                return 60;
            case SELINA:
                return 40;
            case RAIKA:
                return 212;
            case ELIANA:
                return 40;
            case MELINIAS:
                return 50;
            case KASAI:
                return 180;
            case ASANA:
                return 110;
            case LILASIA:
                return 90;
            case MAL:
                return 666;
        }
        return 0;
    }

    public static Integer getVigor(Deity d) {
        switch (d) {
            case FELIXIA:
                return 350;
            case SELINA:
                return 212;
            case RAIKA:
                return 400;
            case ELIANA:
                return 225;
            case MELINIAS:
                return 187;
            case KASAI:
                return 230;
            case ASANA:
                return 875;
            case LILASIA:
                return 333;
            case MAL:
                return 866;
        }
        return 0;
    }

    public static Integer getWillpower(Deity d) {
        switch (d) {
            case FELIXIA:
                return 195;
            case SELINA:
                return 323;
            case RAIKA:
                return 122;
            case ELIANA:
                return 292;
            case MELINIAS:
                return 429;
            case KASAI:
                return 678;
            case ASANA:
                return 200;
            case LILASIA:
                return 333;
            case MAL:
                return 566;
        }
        return 0;
    }
}
