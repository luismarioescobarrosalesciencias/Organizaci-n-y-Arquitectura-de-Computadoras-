import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;



public class Practica01{

  /**
      * Devuelve un arreglo de doubles con los resultados de la media geometrica de cada equipo
      * Datos de las pruebas de los equipos
      * @return media de las pruebas de cada equipo
      */
  public static double[] geometrica(double[][] datos){
    double[] res = new double[datos.length];
    for(int i = 0; i<datos.length;i++){
      res[i] = datos[i][0];
      //System.out.println(res[i]);
      for (int j = 1; j <datos[0].length ;j++){
        //  System.out.println(datos[i][j]+" "+res[i]);
          res[i] *=datos[i][j];
      }
    }

      for(int i = 0; i<res.length;i++){
        //System.out.println(res[i]);
        res[i]= Math.pow(res[i], 1.0 / datos[0].length);
      }
      return res;
}

/**
    * Devuelve un arreglo de doubles con los resultados de la media ponderada de cada equipo
    * Datos de las pruebas de los equipos, los pesos
    * @return media de las pruebas de cada equipo
    */
  public static double[] ponderada(double[][] datos, int w[]){
    double sumaW = 0;
    double[] res = new double[datos.length];
    for(int i = 0; i<datos.length;i++){
      res[i] = datos[i][0]*w[0];
      for (int j = 1; j <datos[0].length ;j++){
          res[i]+= datos[i][j]*w[j];
      }
    }

    for(int i = 0;i<w.length;i++){
      sumaW= sumaW+w[i];
    }
    for(int i = 0; i<res.length;i++){
      res[i]=res[i]/sumaW;
    }
    return res;
  }

  /**
      * normaliza la matriz de datos, segun el tipo de prueba
      * matriz de datos, tipo de prueba
      */
  public static void normalizar(double[][] matriz,String tipo){
      double[] valores_minimos = new double[matriz[0].length] ;
      if(tipo == "-r"){
        //High is better, buscar el mas pequeño y usarlo para diviir
        valores_minimos = valormin(matriz);

      }
      if(tipo == "-t"){
        //Low is better buscar el mas grande y usarlo para dividir
        valores_minimos = valormax(matriz);

      }
      for(int i = 0; i<matriz[0].length;i++){
        for (int j = 0 ; j <matriz.length  ;j++){
            //System.out.println(valores_minimos[i]);
            matriz[j][i]/=valores_minimos[i];
        }
      }
      //divide cada elemento de columna i entre valor[i]

  }

  /**
      * Regresa el valor mas pequeño de cada columna de la matriz
      * Datos de las pruebas de los equipos
      * @return arreglo con los datos mas pequeños por columna
      */
 public static double[] valormin(double [][] matriz){
   double[] valor = new double[matriz[0].length];
   for(int i = 0; i<matriz[0].length;i++){
     valor[i]=matriz[0][i];
     for (int j = 0 ; j <matriz.length  ;j++){
       if(matriz[j][i]<valor[i]){
         valor[i]=matriz[j][i];
       }
     }
   }
   return valor;
}


/**
    * Regresa el valor mas grande de cada columna de la matriz
    * Datos de las pruebas de los equipos
    * @return arreglo con los datos mas grandes por columna
    */
 public static double[] valormax(double[][] matriz){
   double[] valor = new double[matriz[0].length];
   for(int i = 0; i<matriz[0].length;i++){
     valor[i]=matriz[0][i];
     for (int j = 0 ; j <matriz.length  ;j++){
       if(matriz[j][i]>valor[i]){
         valor[i]=matriz[j][i];
       }
     }
   }
   return valor;
}



  public static void main(String args[]) {
//-------------------------------------------------------------
//Genero un set de las banderas validas
    Set<String> bandera_Validas = new HashSet<String>();
    bandera_Validas.add("-g");
    bandera_Validas.add("-p");
    bandera_Validas.add("-t");
    bandera_Validas.add("-r");



  String  [] banderas = new String [args.length-1];
//-------------------------------------------------------------

//almaceno las entradas
    for(int i=0;i<(args.length-1);i++){
      //checar que las banderas sean validas
      if(!(bandera_Validas.contains(args[i]))){
        System.out.println("Las entradsas "+args[i] +" "+args[i+1] +" no son válidas");
        i ++;
      } else {
      banderas[i]  = args[i];
      //System.out.println(args[i]);
      }
    }

//------------------------------------------------------------

//Lectura de archivo y guardado de datos
    String filename;

    filename = args[args.length-1];


    BufferedReader br = null;
      try {
         //Crear un objeto BufferedReader al que se le pasa
         //   un objeto FileReader con el nombre del fichero
         br = new BufferedReader(new FileReader(filename));
         //Leer la primera línea, guardando en un String
         String texto = br.readLine();
         StringTokenizer dato = new StringTokenizer(texto);
         //Repetir mientras no se llegue al final del fichero
         int entradaN = Integer.parseInt(dato.nextToken());
         int resultadosM = Integer.parseInt(dato.nextToken());

         texto = br.readLine();
//------------------------------------------------------------


         //guardamos los pesos
         int pesos[] = new int[resultadosM];
         String[] pesos_str = texto.split(" ");
         for(int i = 0;i < pesos_str.length;i++){
           pesos[i] = Integer.parseInt(pesos_str[i]);
         }
         texto =br.readLine();

         String guarda;


//-------------------------------------------------------------

         double[][] data_set  = new double[entradaN][resultadosM];
         int fila = 0;
          while(texto != null)
          {
            String[] elementos = texto.split(" ");

            for (int i = 0; i < elementos.length; i++)
                data_set[fila][i] = Double.parseDouble(elementos[i]);

            fila++; //Incrementamos fila para la próxima línea de enteros
            texto = br.readLine();
           }
           br.close();


           for(int i = 0; i<banderas.length-1;i++){
             double resultados_finales[] = new double[resultadosM];
             int mejorpc;
             System.out.println(banderas[i]);
              switch(banderas[i]){

               case"-p":
               switch(banderas[i+1]){

                 case "-t":
                   resultados_finales=ponderada(data_set,pesos);
                   double aux0;
                   aux0 = resultados_finales[0];
                   mejorpc=0;
                     for(int m = 0;m<resultados_finales.length-1;m++){
                      if(resultados_finales[m]<aux0){
                        aux0 = resultados_finales[m];
                        mejorpc=m;
                      }

                     }
                     System.out.println("PC"+mejorpc);
                    break;



                 case"-r":
                   resultados_finales=ponderada(data_set,pesos);
                   double aux0_1;
                   aux0_1 = resultados_finales[0];

                   mejorpc=0;
                   for(int m = 0;m<resultados_finales.length-1;m++){
                    if(resultados_finales[m]>aux0_1){
                      aux0_1 = resultados_finales[m];
                      mejorpc=m;
                    }

                   }
                   //System.out.println("hogkk");
                     System.out.println("PC"+mejorpc);
                  break;


             }
             break;
//--------------------------------------------------------------------------------

               case "-g" :

                 switch(banderas[i+1]){
                 case "-t":
                 normalizar(data_set,"-t");
                 resultados_finales=geometrica(data_set);
                 double aux1;
                 aux1 = resultados_finales[0];
                 mejorpc=0;
                   for(int m = 0;m<resultados_finales.length-1;m++){
                    if(resultados_finales[m]<aux1){
                      aux1 = resultados_finales[m];
                      mejorpc=m;
                    }

                   }
                   System.out.println("PC"+mejorpc);
                  break;



                 case"-r":
                   normalizar(data_set,"-r");
                   resultados_finales=geometrica(data_set);
                   for(int g = 0;g<resultados_finales.length;g++){
                     System.out.println(resultados_finales[g]);
                   }
                   double aux2;
                   aux2 = resultados_finales[0];

                   mejorpc=0;
                   for(int m = 0;m<resultados_finales.length-1;m++){
                    if(resultados_finales[m]>aux2){
                      aux2 = resultados_finales[m];
                      mejorpc=m;
                    }

                   }
                   //System.out.println("hogkk");
                     System.out.println("PC"+mejorpc);
                  break;


               }



           }
         }


//matriz de resultado obtenida ----------------------------------------------
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado");
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
//---------------------------------------------------------------------------


   }
}
