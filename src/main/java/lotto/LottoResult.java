package lotto;

import java.util.EnumMap;
import java.util.Map;

public class LottoResult {
    private final Map<Rank, Integer> result;

    public LottoResult() {
        this.result = new EnumMap<>(Rank.class);
        initializeResult();
    }

    private void initializeResult() {
        for (Rank rank : Rank.values()) {
            if (rank != Rank.NONE) {
                result.put(rank, 0);
            }
        }
    }

    public void addResult(Rank rank) {
        if (rank == Rank.NONE) {
            return;
        }
        result.put(rank, result.get(rank) + 1);
    }

    public int getCount(Rank rank) {
        return result.getOrDefault(rank, 0);
    }

    public long getTotalPrizeMoney() {
        return result.entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getPrizeMoney() * entry.getValue())
                .sum();
    }

    public double calculateProfitRate(int purchaseAmount) {
        double profit = (double) getTotalPrizeMoney() / purchaseAmount * 100;
        return Math.round(profit * 10) / 10.0;
    }
}