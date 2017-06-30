/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.utpl.resource.sbc;

/**
 *
 * @author USUARIO
 */
import java.io.FileInputStream;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.ResourceFactory;
import com.hp.hpl.jena.vocabulary.RDF;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GeneracionRDF {

    public void readExcel() throws BiffException, IOException {

        //Resources
        String Country1 = "https://schema.org/Country";
        String series = "http://schema.org/TVSeries";
        String Language1 = "https://schema.org/Language";
        String Organization1 = "https://schema.org/Organization";
        String Person1 = "https://schema.org/Person";
        String PublicationEvent1 = "https://schema.org/PublicationEvent";

        //Object Properties 
        String actor1 = "https://schema.org/actor";
        String director1 = "https://schema.org/director";
        String productionCompany1 = "https://schema.org/productionCompany";
        String inLanguage1 = "https://schema.org/inLanguage";
        String publication1 = "https://schema.org/publication";
        String countryOfOrigin1 = "https://schema.org/countryOfOrigin";

        //Data Properties
        String comment1 = "https://schema.org/comment";
        String description1 = "https://schema.org/description";
        String categ = "https://schema.org/genre";
      
        String name1 = "https://schema.org/name";
        String anio1 = "https://schema.org/anio";
        String duracion = "https://schema.org/duration";
        String numberOfSeasons1 = "https://schema.org/numberOfSeasons";
        String numberOfEpisodes1="https://schema.org/numberOfEpisodes";
        String ratingCount1 = "https://schema.org/ratingCount";
        
        //Propiedades
        Property actor = ResourceFactory.createProperty(actor1);
        Property director = ResourceFactory.createProperty(director1);
        Property inLanguage = ResourceFactory.createProperty(inLanguage1);
        Property productionCompany = ResourceFactory.createProperty(productionCompany1);
        Property comment = ResourceFactory.createProperty(comment1);
        Property categoria = ResourceFactory.createProperty(categ);
        Property countryOfOrigen = ResourceFactory.createProperty(countryOfOrigin1);
        Property name = ResourceFactory.createProperty(name1);
        Property anio = ResourceFactory.createProperty(anio1);
        Property duration = ResourceFactory.createProperty(duracion);
        Property numberOfSeasons = ResourceFactory.createProperty(numberOfSeasons1);
        Property numberOfEpisodes = ResourceFactory.createProperty(numberOfEpisodes1);
        Property rating = ResourceFactory.createProperty(ratingCount1);
        Property description = ResourceFactory.createProperty(description1);
//        Property publication = ResourceFactory.createProperty(publication1);

        //Recursos 
        Resource Country = ResourceFactory.createResource(Country1);
        Resource Series_tv = ResourceFactory.createResource(series);
        Resource Language = ResourceFactory.createResource(Language1);
//        Resource Organization = ResourceFactory.createResource(Organization1);
        Resource Person = ResourceFactory.createResource(Person1);
//        Resource PublicationEvent = ResourceFactory.createResource(PublicationEvent1);

        //Prefijos
        String dataPrefix = "http://example.org/resource/";

        Model model = ModelFactory.createDefaultModel();
        String FilePath = "Series_TV_SBC.xls";
        FileInputStream fs = new FileInputStream(FilePath);
        Workbook wb = Workbook.getWorkbook(fs);

        // TO get the access to the sheet
        Sheet sh = wb.getSheet("data");

        // To get the number of rows present in sheet
        int totalNoOfRows = sh.getRows();


        for (int row = 0; row < totalNoOfRows; row++) {

            
            
                   Resource   Pelicula 
                   
                    = model.createResource(dataPrefix + sh.getCell(0, row).getContents())
                    .addProperty(RDF.type, Series_tv)
                    
                    //nombre de la serie
                    .addProperty(name, sh.getCell(0, row).getContents())
                    
                    //actores
                    .addProperty(actor, model.createResource(dataPrefix + sh.getCell(9, row).getContents())
                            .addProperty(RDF.type, Person)
                            .addProperty(name, sh.getCell(1, row).getContents()))
                    
                    //lenguaje de la pelicula
                    .addProperty(inLanguage, model.createResource(dataPrefix + sh.getCell(2, row).getContents())
                            .addProperty(RDF.type, Language)
                            .addProperty(name, sh.getCell(2, row).getContents()))
                    
                    //categoria de la serie
                    .addProperty(categoria, sh.getCell(3, row).getContents())
                    
                    //Creacion Directores
                    .addProperty(director, model.createResource(dataPrefix + sh.getCell(4, row).getContents())
                            .addProperty(RDF.type, Person)
                            .addProperty(name, sh.getCell(4, row).getContents())
                    )
                    
                    //creacion de productores
                    .addProperty(productionCompany, model.createResource(dataPrefix + sh.getCell(5, row).getContents())
                            .addProperty(RDF.type, Person)
                            .addProperty(name, sh.getCell(5, row).getContents())
                    
                    //descripcion de la serie
                    .addProperty(description, sh.getCell(6, row).getContents())
                            
                    //comentarios de la serie
                    .addProperty(comment, sh.getCell(7, row).getContents())
                    
                    //contry
                    .addProperty(countryOfOrigen, model.createResource(dataPrefix + sh.getCell(8, row).getContents())
                    .addProperty(RDF.type, Country)
                            
                    //temporadas
                    .addProperty(numberOfSeasons, sh.getCell(9, row).getContents())
                    
                    //duracion de la serie
                    .addProperty(duration, sh.getCell(10, row).getContents())
                            
                    //anio
                    .addProperty(anio, sh.getCell(11, row).getContents())
                            
                    //rating
                    .addProperty(rating, sh.getCell(12, row).getContents())
                            
                    )
                    );

            File file = new File("nomArchivo1.rdf");
            //Excepciones
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                }
            }
            model.write(new PrintWriter(file));
        }

        model.write(System.out, "N-TRIPLES");
    }

    public static void main(String args[]) throws BiffException, IOException {
        GeneracionRDF DT = new GeneracionRDF();
        DT.readExcel();
    }
}
