public class Aplicacao {
    private int id;
    private static int contadorAplicacao = 0;
    private Usuario usuario;
    private Criptomoeda criptomoeda;
    private double valorInvestimento;
    private double valorEmCripto;


    public Aplicacao(Usuario nome, Criptomoeda criptomoeda, double valorInvestimento) {
        this.id = ++contadorAplicacao;
        this.usuario = nome;
        this.criptomoeda = criptomoeda;
        this.valorInvestimento = valorInvestimento;
        this.valorEmCripto = setValorEmCripto(this.valorInvestimento);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getNome() {
        return usuario;
    }

    public void setNome(Usuario nome) {
        this.usuario = nome;
    }

    public Criptomoeda getCriptomoeda() {
        return criptomoeda;
    }

    public void setCriptomoeda(Criptomoeda criptomoeda) {
        this.criptomoeda = criptomoeda;
    }

    public double getValorInvestimento() {
        return valorInvestimento;
    }

    public void setValorInvestimento(double valorInvestimento) {
        this.valorInvestimento = valorInvestimento;
    }

    public double getValorEmCripto() {
        return valorEmCripto;
    }

    public double setValorEmCripto(double valor) {
        this.valorEmCripto = valor / criptomoeda.getValorAtual();
        return valorEmCripto;
    }

    public String consultaAplicacao() {

        return  "---------- Consulta da Aplicação ---------"+ "\n" +
                "Cod................." + this.getId() + "\n" +
                "Cliente............." + usuario.getNome() + "\n" +
                "Cripto.............." + criptomoeda.getNome() + "\n" +
                "Valor Unitario......" + String.format("%.2f", this.valorEmCripto) + "\n" +
                "\n"; }
};
