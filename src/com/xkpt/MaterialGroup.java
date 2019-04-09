package com.xkpt;

public class MaterialGroup {
   public    Material[][] material = new Material[GeneticParameter.groupSize][];

   public MaterialGroup() {
      material[0] = new Material[1];
      material[0][0] = new Material(new String("05"), new String("0501"));

      material[1] = new Material[4];
      material[1][0] = new Material(new String("12"), new String("1253"));
      material[1][1] = new Material(new String("02"), new String("0201"));
      material[1][2] = new Material(new String("02"), new String("0202"));
      material[1][3] = new Material(new String("02"), new String("0251"));

      material[2] = new Material[4];
      material[2][0] = new Material(new String("03"), new String("0301"));
      material[2][1] = new Material(new String("03"), new String("0305"));
      material[2][2] = new Material(new String("03"), new String("0351"));
      material[2][3] = new Material(new String("03"), new String("0353"));

      material[3] = new Material[5];
      material[3][0] = new Material(new String("03"), new String("0302"));
      material[3][1] = new Material(new String("03"), new String("0303"));
      material[3][2] = new Material(new String("03"), new String("0304"));
      material[3][3] = new Material(new String("03"), new String("0352"));
      material[3][4] = new Material(new String("01"), new String("0101"));

      material[4] = new Material[5];
      material[4][0] = new Material(new String("03"), new String("0303"));
      material[4][1] = new Material(new String("04"), new String("0401"));
      material[4][2] = new Material(new String("04"), new String("0451"));
      material[4][3] = new Material(new String("04"), new String("0451b"));
      material[4][4] = new Material(new String("04"), new String("0453"));


      material[5] = new Material[7];
      material[5][0] = new Material(new String("12"), new String("1205"));
      material[5][1] = new Material(new String("12"), new String("1255"));
      material[5][2] = new Material(new String("06"), new String("0602"));
      material[5][3] = new Material(new String("06"), new String("0651"));
      material[5][4] = new Material(new String("05"), new String("0503"));
      material[5][5] = new Material(new String("05"), new String("0552"));
      material[5][6] = new Material(new String("05"), new String("0553"));

      material[6] = new Material[5];
      material[6][0] = new Material(new String("08"), new String("0805"));
      material[6][1] = new Material(new String("08"), new String("0817"));
      material[6][2] = new Material(new String("08"), new String("0819"));
      material[6][3] = new Material(new String("07"), new String("0701"));
      material[6][4] = new Material(new String("07"), new String("0703"));

      material[7] = new Material[6];
      material[7][0] = new Material(new String("07"), new String("0702"));
      material[7][1] = new Material(new String("07"), new String("0702"));
      material[7][2] = new Material(new String("07"), new String("0704"));
      material[7][3] = new Material(new String("07"), new String("0705"));
      material[7][4] = new Material(new String("07"), new String("0708"));
      material[7][5] = new Material(new String("07"), new String("0709"));
      material[7][5] = new Material(new String("07"), new String("0711"));

      material[8] = new Material[6];
      material[8][0] = new Material(new String("08"), new String("0801"));
      material[8][1] = new Material(new String("08"), new String("0802"));
      material[8][2] = new Material(new String("08"), new String("0803"));
      material[8][3] = new Material(new String("08"), new String("0817"));
      material[8][4] = new Material(new String("08"), new String("0852"));
      material[8][5] = new Material(new String("08"), new String("0852b"));


      material[9] = new Material[7];
      material[9][0] = new Material(new String("08"), new String("0804"));
      material[9][1] = new Material(new String("08"), new String("0808"));
      material[9][2] = new Material(new String("08"), new String("0809"));
      material[9][3] = new Material(new String("08"), new String("0810"));
      material[9][4] = new Material(new String("08"), new String("0811"));
      material[9][5] = new Material(new String("08"), new String("0812"));
      material[9][6] = new Material(new String("08"), new String("0835"));

      material[10] = new Material[8];
      material[10][0] = new Material(new String("08"), new String("0807"));
      material[10][1] = new Material(new String("08"), new String("0813"));
      material[10][2] = new Material(new String("08"), new String("0814"));
      material[10][3] = new Material(new String("08"), new String("0815"));
      material[10][4] = new Material(new String("08"), new String("0821"));
      material[10][5] = new Material(new String("08"), new String("0822"));
      material[10][6] = new Material(new String("08"), new String("0837"));
      material[10][7] = new Material(new String("08"), new String("0851"));

      material[11] = new Material[9];
      material[11][0] = new Material(new String("08"), new String("0830"));
      material[11][1] = new Material(new String("08"), new String("0831"));
      material[11][2] = new Material(new String("08"), new String("0836"));
      material[11][3] = new Material(new String("08"), new String("0815"));
      material[11][4] = new Material(new String("07"), new String("0710"));
      material[11][5] = new Material(new String("07"), new String("0713"));
      material[11][6] = new Material(new String("09"), new String("0907"));
      material[11][7] = new Material(new String("09"), new String("0952"));
      material[11][8] = new Material(new String("09"), new String("0952b"));

      material[12] = new Material[10];
      material[12][0] = new Material(new String("08"), new String("0832"));
      material[12][1] = new Material(new String("08"), new String("0813"));
      material[12][2] = new Material(new String("12"), new String("1203"));
      material[12][3] = new Material(new String("09"), new String("0902"));
      material[12][4] = new Material(new String("09"), new String("0903"));
      material[12][5] = new Material(new String("09"), new String("0904"));
      material[12][6] = new Material(new String("09"), new String("0905"));
      material[12][7] = new Material(new String("09"), new String("0908"));
      material[12][8] = new Material(new String("09"), new String("0951"));
      material[12][9] = new Material(new String("09"), new String("0953"));

      material[13] = new Material[12];
      material[13][0] = new Material(new String("10"), new String("1001"));
      material[13][1] = new Material(new String("10"), new String("1004"));
      material[13][2] = new Material(new String("10"), new String("1006"));
      material[13][3] = new Material(new String("10"), new String("1007"));
      material[13][4] = new Material(new String("10"), new String("1010"));
      material[13][5] = new Material(new String("10"), new String("1011"));
      material[13][6] = new Material(new String("10"), new String("1051"));
      material[13][7] = new Material(new String("10"), new String("1052"));
      material[13][8] = new Material(new String("10"), new String("1053"));
      material[13][9] = new Material(new String("10"), new String("1054"));
      material[13][10] = new Material(new String("10"), new String("1055"));
      material[13][11] = new Material(new String("10"), new String("1056"));

      material[14] = new Material[6];
      material[14][0] = new Material(new String("12"), new String("1201"));
      material[14][1] = new Material(new String("12"), new String("1202"));
      material[14][2] = new Material(new String("12"), new String("1204"));
      material[14][3] = new Material(new String("12"), new String("1252"));
      material[14][4] = new Material(new String("02"), new String("0252"));
      material[14][5] = new Material(new String("02"), new String("0254"));

      material[15] = new Material[4];
      material[15][0] = new Material(new String("13"), new String("1303"));
      material[15][1] = new Material(new String("13"), new String("1304"));
      material[15][2] = new Material(new String("13"), new String("1305"));
      material[15][3] = new Material(new String("13"), new String("1351"));

      material[16] = new Material[4];
      material[16][0] = new Material(new String("04"), new String("0402"));
      material[16][1] = new Material(new String("04"), new String("0403"));
      material[16][2] = new Material(new String("04"), new String("0452"));
      material[16][3] = new Material(new String("04"), new String("0454"));
   }

}
