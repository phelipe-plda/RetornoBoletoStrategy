package com.manoelcampos.retornoboleto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Realiza a leitura de arquivos de retorno de boletos bancários no formato do Banco do Brasil.
 * Esta classe usa o padrão Strategy, representando a implementação da estratégia {@link LeituraRetorno}.
 *
 * @author Manoel Campos da Silva Filho
 */
 // tag::class-start[]
public class LeituraRetornoBancoBradesco implements LeituraRetorno {

    @Override
    public List<Boleto> lerArquivo(URI nomeArquivo) {
        
        imprimirBanco();
        
        try {
            BufferedReader reader = 
                Files.newBufferedReader(Paths.get(nomeArquivo));
            String line;
            List<Boleto> boletos = new ArrayList<>();
            while((line = reader.readLine()) != null){
                String[] vetor = line.split(";");
                Boleto boleto = new Boleto();
                boleto.setId(Integer.parseInt(vetor[0]));
                boleto.setCodBanco(vetor[1]);
                // end::class-start[]
                boleto.setAgencia(vetor[2]);
                // end::class-start[]
                boleto.setContaBancaria(vetor[3]);
                
                
                boleto.setDataVencimento(LocalDate.parse(vetor[4], FORMATO_DATA));
                boleto.setDataPagamento(LocalDateTime.parse(vetor[5], FORMATO_DATA_HORA));

                boleto.setCpfCliente(vetor[6]);
                boleto.setValor(Double.parseDouble(vetor[7]));
                boleto.setMulta(Double.parseDouble(vetor[8]));
                boleto.setJuros(Double.parseDouble(vetor[9]));

                // tag::class-end[]
                boletos.add(boleto);
                
                
            }

            return boletos;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        
        
    }
    
    
    
    public void imprimirBanco(){
        System.out.println("Banco Bradesco"); 
    };
}
 // end::class-end[]
