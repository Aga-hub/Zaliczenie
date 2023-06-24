package Program;

import java.awt.image.BufferedImage;

public class Program {
    static int k=0;
    static int b=0; //zmienne do obsługi tablicy w filtrze medianowym

    private static void dodaj( int[] a , int[][] o, int c, int d){ //metoda do dodawania wartości do tablicy z wartościami do mediany, by piksele bez kwadratowego okna (zbyt blisko krawędzi) nie były błędnie przetwarzane
        if(c>=0 && c<=(o.length-1) && d>=0 && d<=(o[0].length-1)){
            a[k]=o[c][d];
            k++; //indeks tablicy - każdy kolejny piksel zostaje dodany do kolejnego miejsca
        }
        else{
            a[k]=255; //"błedne piksele" znajda sie (po sortowaniu) na koncu tablicy
            k++;
            b++; //ilość "błędnych" pikseli - wychodzących za obrazek - za pomoca tej zmiennej piksele bledne znajdujace sie na koncu zostana zignorowane
        }
    }

    public static BufferedImage jasnosc(BufferedImage obrazOryg , int P){ //metoda zmiany jasności
        int wysokosc = obrazOryg.getHeight();
        int dlugosc = obrazOryg.getWidth();
        BufferedImage obrazFiltrowany = obrazOryg;
        int[][] pixeleOrygR = new int [dlugosc][wysokosc];
        int[][] pixeleOrygG = new int [dlugosc][wysokosc];
        int[][] pixeleOrygB = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrR = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrG = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrB = new int [dlugosc][wysokosc]; //stworzenie tablic na zapisywane wartości RGB

        for(int d=0 ; d<dlugosc ; d++){ //zapisanie tablic oryginalnego obrazka wartościami RGB
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = obrazOryg.getRGB(d, w);
                pixeleOrygR[d][w]=(rgb >> 16) & 0xFF;
                pixeleOrygG[d][w]=(rgb >> 8) & 0xFF;
                pixeleOrygB[d][w]=(rgb) & 0xFF;
            }
        }

        for(int d=0 ; d<dlugosc ; d++){ //zmiana jasności
            for(int w=0 ; w<wysokosc ; w++){
                if((pixeleOrygR[d][w]+P)<=255 && (pixeleOrygR[d][w]+P)>=0) {
                    pixeleFiltrR[d][w] = pixeleOrygR[d][w] + P;
                }
                else if((pixeleOrygR[d][w]+P)>255){
                    pixeleFiltrR[d][w] = 255;
                }
                else{
                    pixeleFiltrR[d][w] = 0;
                } //R

                if((pixeleOrygG[d][w]+P)<=255 && (pixeleOrygG[d][w]+P)>=0) {
                    pixeleFiltrG[d][w] = pixeleOrygG[d][w] + P;
                }
                else if((pixeleOrygG[d][w]+P)>255){
                    pixeleFiltrG[d][w] = 255;
                }
                else{
                    pixeleFiltrG[d][w] = 0;
                } //G

                if((pixeleOrygB[d][w]+P)<=255 && (pixeleOrygB[d][w]+P)>=0) {
                    pixeleFiltrB[d][w] = pixeleOrygB[d][w] + P;
                }
                else if((pixeleOrygB[d][w]+P)>255){
                    pixeleFiltrB[d][w] = 255;
                }
                else{
                    pixeleFiltrB[d][w] = 0;
                }//B
            }
        }


        for(int d=0 ; d<dlugosc ; d++){ //sklejanie tablic w obraz przefiltrowany
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = (pixeleFiltrR[d][w] << 16 | pixeleFiltrG[d][w] << 8 | pixeleFiltrB[d][w]);
                obrazFiltrowany.setRGB(d, w, rgb);
            }
        }

        return obrazFiltrowany;
    }

    public static BufferedImage kontrast(BufferedImage obrazOryg , int P){ //metoda zmiany kontrastu
        int wysokosc = obrazOryg.getHeight();
        int dlugosc = obrazOryg.getWidth();
        BufferedImage obrazFiltrowany = obrazOryg;
        int[][] pixeleOrygR = new int [dlugosc][wysokosc];
        int[][] pixeleOrygG = new int [dlugosc][wysokosc];
        int[][] pixeleOrygB = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrR = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrG = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrB = new int [dlugosc][wysokosc]; //stworzenie tablic na zapisywane wartości RGB
        int maxR = 0; //maksymalna wartość R
        int maxG = 0; //maksymalna wartość G
        int maxB = 0; //maksymalna wartość B


        for(int d=0 ; d<dlugosc ; d++){ //zapisanie tablic oryginalnego obrazka wartościami RGB
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = obrazOryg.getRGB(d, w);
                pixeleOrygR[d][w]=(rgb >> 16) & 0xFF;
                if(pixeleOrygR[d][w]>maxR){
                    maxR=pixeleOrygR[d][w];
                }
                pixeleOrygG[d][w]=(rgb >> 8) & 0xFF;
                if(pixeleOrygG[d][w]>maxG){
                    maxG=pixeleOrygG[d][w];
                }
                pixeleOrygB[d][w]=(rgb) & 0xFF;
                if(pixeleOrygB[d][w]>maxB){
                    maxB=pixeleOrygB[d][w];
                }
            }
        }


        for(int d=0 ; d<dlugosc ; d++){ //zmiana kontrastu
            for(int w=0 ; w<wysokosc ; w++){
                if((P*(pixeleOrygR[d][w]-(maxR/2))+(maxR/2))<=255 && (P*(pixeleOrygR[d][w]-(maxR/2))+(maxR/2))>=0 ) {
                    pixeleFiltrR[d][w] = P*(pixeleOrygR[d][w]-(maxR/2))+(maxR/2);
                }
                else if((P*(pixeleOrygR[d][w]-(maxR/2))+(maxR/2))<0){
                    pixeleFiltrR[d][w] = 0;
                }
                else{
                    pixeleFiltrR[d][w] = 255;
                } //R

                if((P*(pixeleOrygG[d][w]-(maxR/2))+(maxR/2))<=255 && (P*(pixeleOrygG[d][w]-(maxR/2))+(maxR/2))>=0 ) {
                    pixeleFiltrG[d][w] = P*(pixeleOrygG[d][w]-(maxR/2))+(maxR/2);
                }
                else if((P*(pixeleOrygG[d][w]-(maxR/2))+(maxR/2))<0){
                    pixeleFiltrG[d][w] = 0;
                }
                else{
                    pixeleFiltrG[d][w] = 255;
                } //G

                if((P*(pixeleOrygB[d][w]-(maxR/2))+(maxR/2))<=255  && (P*(pixeleOrygB[d][w]-(maxR/2))+(maxR/2))>=0 ) {
                    pixeleFiltrB[d][w] = P*(pixeleOrygB[d][w]-(maxR/2))+(maxR/2);
                }
                else if((P*(pixeleOrygR[d][w]-(maxR/2))+(maxR/2))<0){
                    pixeleFiltrB[d][w] = 0;
                }
                else{
                    pixeleFiltrB[d][w] = 255;
                }//B
            }
        }


        for(int d=0 ; d<dlugosc ; d++){ //sklejanie tablic w obraz przefiltrowany
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = (pixeleFiltrR[d][w] << 16 | pixeleFiltrG[d][w] << 8 | pixeleFiltrB[d][w]);
                obrazFiltrowany.setRGB(d, w, rgb);
            }
        }

        return obrazFiltrowany;
    }



    public static BufferedImage progowanie(BufferedImage obrazOryg , int P){ //metoda progowania
        int wysokosc = obrazOryg.getHeight();
        int dlugosc = obrazOryg.getWidth();
        BufferedImage obrazFiltrowany = obrazOryg;
        int[][] pixeleOrygR = new int [dlugosc][wysokosc];
        int[][] pixeleOrygG = new int [dlugosc][wysokosc];
        int[][] pixeleOrygB = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrR = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrG = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrB = new int [dlugosc][wysokosc]; //stworzenie tablic na zapisywane wartości RGB

        for(int d=0 ; d<dlugosc ; d++){ //zapisanie tablic oryginalnego obrazka wartościami RGB
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = obrazOryg.getRGB(d, w);
                pixeleOrygR[d][w]=(rgb >> 16) & 0xFF;
                pixeleOrygG[d][w]=(rgb >> 8) & 0xFF;
                pixeleOrygB[d][w]=(rgb) & 0xFF;
            }
        }

        for(int d=0 ; d<dlugosc ; d++){ //progowanie
            for(int w=0 ; w<wysokosc ; w++){
                if(pixeleOrygR[d][w]>=P){
                    pixeleFiltrR[d][w]=255;
                }
                else{
                    pixeleFiltrR[d][w]=0;
                }//R

                if(pixeleOrygG[d][w]>=P){
                    pixeleFiltrG[d][w]=255;
                }
                else{
                    pixeleFiltrG[d][w]=0;
                }//G

                if(pixeleOrygB[d][w]>=P){
                    pixeleFiltrB[d][w]=255;
                }
                else{
                    pixeleFiltrB[d][w]=0;
                }
            }
        }


        for(int d=0 ; d<dlugosc ; d++){ //sklejanie tablic w obraz przefiltrowany
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = (pixeleFiltrR[d][w] << 16 | pixeleFiltrG[d][w] << 8 | pixeleFiltrB[d][w]);
                obrazFiltrowany.setRGB(d, w, rgb);
            }
        }
        return obrazFiltrowany;
    }

    public static BufferedImage filtrMedianowy(BufferedImage obrazOryg , int N) { //filtr medianowy
        int wysokosc = obrazOryg.getHeight();
        int dlugosc = obrazOryg.getWidth();
        BufferedImage obrazFiltrowany = obrazOryg;
        int[][] pixeleOrygR = new int [dlugosc][wysokosc];
        int[][] pixeleOrygG = new int [dlugosc][wysokosc];
        int[][] pixeleOrygB = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrR = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrG = new int [dlugosc][wysokosc];
        int[][] pixeleFiltrB = new int [dlugosc][wysokosc]; //stworzenie tablic na zapisywane wartości RGB
        int ilosc_do_mediany=0;
        float mediana = 0;
        for(int i=N ; i>0 ;i--){
            ilosc_do_mediany=ilosc_do_mediany+(i*8); //wyliczanie ile pikseli znajduje sie w ustalonym oknie filtracji
        }
        int [] pixeleMediana = new int [ilosc_do_mediany]; //tablica na piksele do wyliczania mediany
        for(int d=0 ; d<dlugosc ; d++){ //zapisanie tablic oryginalnego obrazka wartościami RGB
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = obrazOryg.getRGB(d, w);
                pixeleOrygR[d][w]=(rgb >> 16) & 0xFF;
                pixeleOrygG[d][w]=(rgb >> 8) & 0xFF;
                pixeleOrygB[d][w]=(rgb) & 0xFF;
            }
        }

        for(int d=0 ; d<dlugosc ; d++) { //R
            for (int w = 0; w < wysokosc; w++) {
                for(int j=N; j>=1; j--) {
                    dodaj(pixeleMediana, pixeleOrygR, d, w + j);
                    dodaj(pixeleMediana, pixeleOrygR, d + j, w);
                    dodaj(pixeleMediana, pixeleOrygR, d, w - j);
                    dodaj(pixeleMediana, pixeleOrygR, d - j, w);//wartości R pod, nad, z lewej i prawej piksela
                    dodaj(pixeleMediana, pixeleOrygR, d - j, w - j);
                    dodaj(pixeleMediana, pixeleOrygR, d - j, w + j);
                    dodaj(pixeleMediana, pixeleOrygR, d + j, w - j);
                    dodaj(pixeleMediana, pixeleOrygR, d + j, w + j);//R "rogi" okna
                    for (int i = j - 1; i > 0; i--) {
                        if(j>1) {
                            dodaj(pixeleMediana, pixeleOrygR, d + i, w + j);
                            dodaj(pixeleMediana, pixeleOrygR, d - i, w + j);
                            dodaj(pixeleMediana, pixeleOrygR, d + j, w + i);
                            dodaj(pixeleMediana, pixeleOrygR, d + j, w - i);
                            dodaj(pixeleMediana, pixeleOrygR, d - i, w - j);
                            dodaj(pixeleMediana, pixeleOrygR, d + i, w - j);
                            dodaj(pixeleMediana, pixeleOrygR, d - j, w + i);
                            dodaj(pixeleMediana, pixeleOrygR, d - j, w - i);//wartości R między "rogami" i wartościami "na krzyż", czym większe okno tym więcej tych wartości, dlatego wymagana była pętla
                        }
                    }
                }

                java.util.Arrays.sort(pixeleMediana);
                if(((ilosc_do_mediany-b)%2)==0){
                    mediana = ((pixeleMediana[((ilosc_do_mediany-b)/2)-1]+pixeleMediana[((ilosc_do_mediany-b)/2)])/2);
                    pixeleFiltrR[d][w] = (int) mediana;
                }
                else{
                    mediana = pixeleMediana[(ilosc_do_mediany-b-1)/2];
                    pixeleFiltrR[d][w]= (int) mediana;
                }
                k=0;
                b=0;
            }
        }
        for(int d=0 ; d<dlugosc ; d++) { //G
            for (int w = 0; w < wysokosc; w++) {
                for(int j=N; j>=1; j--) {
                    dodaj(pixeleMediana, pixeleOrygG, d, w + j);
                    dodaj(pixeleMediana, pixeleOrygG, d + j, w);
                    dodaj(pixeleMediana, pixeleOrygG, d, w - j);
                    dodaj(pixeleMediana, pixeleOrygG, d - j, w);//wartości G pod, nad, z lewej i prawej piksela
                    dodaj(pixeleMediana, pixeleOrygG, d - j, w - j);
                    dodaj(pixeleMediana, pixeleOrygG, d - j, w + j);
                    dodaj(pixeleMediana, pixeleOrygG, d + j, w - j);
                    dodaj(pixeleMediana, pixeleOrygG, d + j, w + j);//G "rogi" okna
                    for (int i = j - 1; i > 0; i--) {
                        if(j>1) {
                            dodaj(pixeleMediana, pixeleOrygG, d + i, w + j);
                            dodaj(pixeleMediana, pixeleOrygG, d - i, w + j);
                            dodaj(pixeleMediana, pixeleOrygG, d + j, w + i);
                            dodaj(pixeleMediana, pixeleOrygG, d + j, w - i);
                            dodaj(pixeleMediana, pixeleOrygG, d - i, w - j);
                            dodaj(pixeleMediana, pixeleOrygG, d + i, w - j);
                            dodaj(pixeleMediana, pixeleOrygG, d - j, w + i);
                            dodaj(pixeleMediana, pixeleOrygG, d - j, w - i);//wartości G między "rogami" i wartościami "na krzyż", czym większe okno tym więcej tych wartości, dlatego wymagana była pętla
                        }
                    }
                }


                java.util.Arrays.sort(pixeleMediana);
                if(((ilosc_do_mediany-b)%2)==0){
                    mediana = ((pixeleMediana[((ilosc_do_mediany-b)/2)-1]+pixeleMediana[((ilosc_do_mediany-b)/2)])/2);
                    pixeleFiltrG[d][w] = (int) mediana;
                }
                else{
                    mediana = pixeleMediana[(ilosc_do_mediany-b-1)/2];
                    pixeleFiltrG[d][w]= (int) mediana;
                }
                k=0;
                b=0;
            }
        }
        for(int d=0 ; d<dlugosc ; d++) { //B
            for (int w = 0; w < wysokosc; w++) {
                for(int j=N; j>=1; j--) {
                    dodaj(pixeleMediana, pixeleOrygB, d, w + j);
                    dodaj(pixeleMediana, pixeleOrygB, d + j, w);
                    dodaj(pixeleMediana, pixeleOrygB, d, w - j);
                    dodaj(pixeleMediana, pixeleOrygB, d - j, w);//wartości B pod, nad, z lewej i prawej piksela
                    dodaj(pixeleMediana, pixeleOrygB, d - j, w - j);
                    dodaj(pixeleMediana, pixeleOrygB, d - j, w + j);
                    dodaj(pixeleMediana, pixeleOrygB, d + j, w - j);
                    dodaj(pixeleMediana, pixeleOrygB, d + j, w + j);//B "rogi" okna
                    for (int i = j - 1; i > 0; i--) {
                        if(j>1) {
                            dodaj(pixeleMediana, pixeleOrygB, d + i, w + j);
                            dodaj(pixeleMediana, pixeleOrygB, d - i, w + j);
                            dodaj(pixeleMediana, pixeleOrygB, d + j, w + i);
                            dodaj(pixeleMediana, pixeleOrygB, d + j, w - i);
                            dodaj(pixeleMediana, pixeleOrygB, d - i, w - j);
                            dodaj(pixeleMediana, pixeleOrygB, d + i, w - j);
                            dodaj(pixeleMediana, pixeleOrygB, d - j, w + i);
                            dodaj(pixeleMediana, pixeleOrygB, d - j, w - i);//wartości B między "rogami" i wartościami "na krzyż", czym większe okno tym więcej tych wartości, dlatego wymagana była pętla
                        }
                    }
                }


                java.util.Arrays.sort(pixeleMediana); //sortowanie tablicy
                //ustalenie mediany
                if(((ilosc_do_mediany-b)%2)==0){
                    mediana = ((pixeleMediana[((ilosc_do_mediany-b)/2)-1]+pixeleMediana[((ilosc_do_mediany-b)/2)])/2);
                    pixeleFiltrB[d][w] = (int) mediana;
                }
                else{
                    mediana = pixeleMediana[(ilosc_do_mediany-b-1)/2];
                    pixeleFiltrB[d][w]= (int) mediana;
                }
                k=0;
                b=0; //wyzerowanie indeksów dla następnego piksela
            }
        }

        for(int d=0 ; d<dlugosc ; d++){ //sklejanie tablic w obraz przefiltrowany
            for(int w=0 ; w<wysokosc ; w++){
                int rgb = (pixeleFiltrR[d][w] << 16 | pixeleFiltrG[d][w] << 8 | pixeleFiltrB[d][w]);
                obrazFiltrowany.setRGB(d, w, rgb);
            }
        }
        return obrazFiltrowany;
    }
}
