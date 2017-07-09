
package geofusion;

import java.io.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Geofusion {
    
    /*
    Usei a biblioteca JSoup da Java
    que é responsável em se conectar e ler uma página web 
    E usei sua classe Elements para pegar os elementos(Dados) do site
    por meio das tag
    tag table e td
    */

    //Document onde ficará armazenadas as informações da pagina WEB
    private Document doc;

    public Geofusion(Document doc) {
        this.doc = doc;
    }

    public static void main(String[] args) throws IOException {
        //Armazena tudo que fora capturado do site passado
        Document document = Jsoup.connect("http://cnes2.datasus.gov.br/Mod_Ind_Equipamentos_Listar.asp?VCod_Equip=87&VTipo_Equip=8&VListar=1&VEstado=25&VMun=&VComp=").get();

        Geofusion dados = new Geofusion(document);
        //Método onde irá pegar as informações
        dados.getPage();
       
    }

    public void getPage() throws IOException {
        //Puxa dados da tag Tabela onde está as informações
        Elements pegarDado = doc.getElementsByTag("Table");

        String text = null;
        for (Element el : pegarDado) {
            //variável "text' recebe os dados da tag td da table do html, onde estão as linhas
            //Método text é só para pegar apenas o texto conido no código e não o HTML
            text = el.getElementsByTag("td").text();
        }
        //Método onde irá salvar os dados em um arquivo CSV
        salvarDados(text);

    }

    private void salvarDados(String dados) throws IOException {
        //Onde ficará salvo o arquivo CSV
        File arq = new File("C:\\Users\\junio\\Documents\\GitHub\\Geofusion\\Geofusion\\DadosWeb.CSV");
        
        //Este buffered é usado para gravar arquivo irá ser usado para escrever no arquivo
        BufferedWriter out = new BufferedWriter(new FileWriter(arq));
        String inputLine;
        while ((inputLine = dados) != null) {
            // escreve os dados na pagina no arquivo
            out.write(inputLine);

            //Break para quebrar o While e sair do Loop
            break;
        }
        
        //Grava e fecha o BufferedWriter
        out.flush();
        out.close();
        
        System.out.println("Arquivo salvo com sucesso");
    }

}
