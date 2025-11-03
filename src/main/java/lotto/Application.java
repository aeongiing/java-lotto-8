package lotto;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LottoMachine lottoMachine = new LottoMachine();
        LottoController controller = new LottoController(inputView, outputView, lottoMachine);

        controller.run();
    }
}