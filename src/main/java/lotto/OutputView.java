package lotto;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_MESSAGE = "\n%d개를 구매했습니다.";
    private static final String STATISTICS_HEADER = "\n당첨 통계\n---";
    private static final String MATCH_FORMAT = "%d개 일치 (%s원) - %d개";
    private static final String MATCH_BONUS_FORMAT = "%d개 일치, 보너스 볼 일치 (%s원) - %d개";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %.1f%%입니다.";

    public void printLottos(List<Lotto> lottos) {
        System.out.printf(PURCHASE_MESSAGE, lottos.size());
        System.out.println();
        lottos.forEach(lotto -> System.out.println(lotto.getNumbers()));
    }

    public void printResult(LottoResult result, int purchaseAmount) {
        System.out.println(STATISTICS_HEADER);
        printRankStatistics(result);
        printProfitRate(result, purchaseAmount);
    }

    private void printRankStatistics(LottoResult result) {
        System.out.println(formatRank(Rank.FIFTH, result));
        System.out.println(formatRank(Rank.FOURTH, result));
        System.out.println(formatRank(Rank.THIRD, result));
        System.out.println(formatRankWithBonus(Rank.SECOND, result));
        System.out.println(formatRank(Rank.FIRST, result));
    }

    private String formatRank(Rank rank, LottoResult result) {
        return String.format(
                MATCH_FORMAT,
                rank.getMatchCount(),
                formatMoney(rank.getPrizeMoney()),
                result.getCount(rank)
        );
    }

    private String formatRankWithBonus(Rank rank, LottoResult result) {
        return String.format(
                MATCH_BONUS_FORMAT,
                rank.getMatchCount(),
                formatMoney(rank.getPrizeMoney()),
                result.getCount(rank)
        );
    }

    private String formatMoney(int money) {
        return String.format("%,d", money);
    }

    private void printProfitRate(LottoResult result, int purchaseAmount) {
        double profitRate = result.calculateProfitRate(purchaseAmount);
        System.out.printf(PROFIT_RATE_FORMAT, profitRate);
        System.out.println();
    }

    public void printError(String message) {
        System.out.println(message);
    }
}