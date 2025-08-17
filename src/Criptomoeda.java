import java.sql.Connection;

public class Criptomoeda {
    private Integer idCript;
    //private static int contadorCripto = 0;
    private String nome;
    private String sigla;
    private double valorAtual;


    public Criptomoeda(String nome, String sigla, double valorAtual) {
        //this.idCript = ++contadorCripto;
        this.nome = nome;
        this.sigla = sigla;
        this.valorAtual = valorAtual;

    }

    public int getIdCript() {
        return idCript;
    }

    public void setIdCript(int idCript) {
        this.idCript = idCript;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public double getValorAtual() {
        return valorAtual;
    }

    public void setValorAtual(double valorAtual) {
        this.valorAtual = valorAtual;
    }

}