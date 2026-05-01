package relatorio;
//https://www.youtube.com/watch?v=V1Ijk5kJr9k

import controle.TabelaNutricionalControle;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;

import net.sf.jasperreports.view.JasperViewer;

public class DocumentoTabelaNutricional {
    
    public void criarRelatorio(int tabCodigo){
        TabelaNutricionalControle tbc = new TabelaNutricionalControle();
        try{
            JRResultSetDataSource resultSet = 
                    new JRResultSetDataSource(tbc.consultarTabelasNutricionaisResultSet(tabCodigo));
            String jpPrint = JasperRunManager.runReportToPdfFile("./TabelaNutricional.jasper", 
                    new HashMap<>(), resultSet);
            
            JasperViewer jv = new JasperViewer(jpPrint, false);
            jv.setVisible(true);
            jv.toFront();  
        }
        catch(JRException e){
            System.out.println(e);
        }
    }
    
     public JRResultSetDataSource criarRelatorioJR(int tabCodigo){
         TabelaNutricionalControle tbc = new TabelaNutricionalControle();
         JRResultSetDataSource resultSet = 
                    new JRResultSetDataSource(tbc.consultarTabelasNutricionaisResultSet(tabCodigo));
         return resultSet;
     }
     
         
    public static void main(String[] args) {
        DocumentoTabelaNutricional d = new DocumentoTabelaNutricional();
        d.criarRelatorio(22);
    }
    
}
