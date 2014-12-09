package me.naithantu.ExpVault;

public class ExpUtil {
	public static int getXpFrom(int level, float progress, int levelsToRemove){
		int i = 0;
		int totalXp = 0;
		while(i < levelsToRemove){
			totalXp += getXpFromLastLevel(level - i, progress);
			i++;
		}
		return totalXp;
	}
	public static int getXpFromLast(int level) {
		if (level >= 31) {
			return (9 * level) - 158;
		} else if (level >= 16) {
            return (5 * level) - 38;
        } else {
            return (2 * level) + 7;
        }
	}

	public static int getXpFromLastLevel(int level, float progress) {
		return getTotalExperience(level, progress) - getTotalExperience(level - 1, progress);
	}

	public static int getTotalExperience(int level, float progress) {
		int exp = (int) Math.round(getXpFromLast(level) * progress);

		while (level > 0) {
			level--;
			exp += getXpFromLast(level);
		}
		if (exp < 0) {
			exp = Integer.MAX_VALUE;
		}
		return exp;
	}
}
