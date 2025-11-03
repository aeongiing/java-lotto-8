package lotto;

import java.util.List;
import java.util.function.Supplier;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoController(InputView inputView, OutputView outputView, LottoMachine lottoMachine) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoMachine = lottoMachine;
    }

    public void run() {
        int purchaseAmount = readPurchaseAmountWithRetry();
        List<Lotto> lottos = lottoMachine.purchaseLottos(purchaseAmount);
        outputView.printLottos(lottos);

        WinningNumbers winningNumbers = readWinningNumbersWithRetry();
        LottoResult result = checkLottos(lottos, winningNumbers);
        outputView.printResult(result, purchaseAmount);
    }

    private int readPurchaseAmountWithRetry() {
        return executeWithRetry(inputView::readPurchaseAmount);
    }

    private WinningNumbers readWinningNumbersWithRetry() {
        List<Integer> winningNumberList = executeWithRetry(inputView::readWinningNumbers);
        int bonusNumber = executeWithRetry(inputView::readBonusNumber);
        return executeWithRetry(() -> new WinningNumbers(winningNumberList, bonusNumber));
    }

    private <T> T executeWithRetry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private LottoResult checkLottos(List<Lotto> lottos, WinningNumbers winningNumbers) {
        LottoResult result = new LottoResult();
        for (Lotto lotto : lottos) {
            Rank rank = winningNumbers.match(lotto);
            result.addResult(rank);
        }
        return result;
    }
}