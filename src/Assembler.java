 
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 
 
public class Assembler
{
    public static void main(String[]args)throws IOException
    {
        int check;
        int[] A= new int[16];
        int[] C= new int[] {1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0};
        int y=0,P=0, T=0;       
        int Length=0, Length2=0;
        char c=' ';
        char q=' ';
        char Z=' ';
        String lineNumber="", line="";
        String comp="";
        String JUMP="";
        Map<String,Integer> symbolTable = new HashMap<String,Integer>();
       
        
        Scanner scan= new Scanner(new File("Pong.asm"));
        FileWriter fw = new FileWriter("Pongout.hack");
       
        String FILE ="";
        int position=0;
        int counter=16;
        String test="",count="";
       
        //reads file taking out whitespace, comments, and blank line
        while(scan.hasNext())
        {
            line=scan.nextLine();
            Length=line.length();
            
            
                line=line.replaceAll("\\s","");
                Length=line.length();          
            
                for(int J=0; J<Length; J++)
                {
                    c=line.charAt(J);
                    if(c=='/')
                    {
                        line=line.substring(0,J);
                        break;
                    }
                }
           
            Length=line.length();
            if(Length>0)
            {
                FILE=FILE.concat(line);
                FILE=FILE.concat("\n");
            }
        }
       
        
        //reads through the string FILE and replaces labels with the corresponding line number
        Scanner scan1 = new Scanner(FILE);
       
         while(scan1.hasNext())
        {
           
            line=scan1.nextLine();
            Length=line.length();
           
             c=line.charAt(0);
             q=line.charAt(Length-1);
            
             if(c =='(' && q==')')
             {
               
                 lineNumber=line;
                 lineNumber=lineNumber.replace("(","");
                 lineNumber=lineNumber.replace(")","");
                 lineNumber="@"+lineNumber;
                
                 FILE=FILE.replace(line+"\n","");
                
                 count=Integer.toString(position);
                 FILE=FILE.replace(lineNumber+"\n","@"+count+"\n");
                 position=position-1;
                }
            position++;
        }
       
        
        //reads through the string FILE a second time and replaces variables with the a memory location
        Scanner scan2 = new Scanner(FILE);
 
        while(scan2.hasNext())
        {
            line=scan2.nextLine();
            Length=line.length();
           
             c=line.charAt(0);
            
             if(c =='@')
             {
                 lineNumber=line.replace("@","");
                 Length=lineNumber.length();
                 try
                    {
                       
                        y= Integer.parseInt(lineNumber);
                    }
                 catch(java.lang.NumberFormatException e)
                    { 
                        if(lineNumber.equals("SP") || lineNumber.equals("R0") )
                        {
                            FILE=FILE.replaceAll(lineNumber,"0");
                        }
                        else  if(lineNumber.equals("LCL") || lineNumber.equals("R1"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"1");
                        }
                        else  if(lineNumber.equals("ARG") || lineNumber.equals("R2"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"2");
                        }
                        else  if(lineNumber.equals("THIS") || lineNumber.equals("R3"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"3");
                        }
                        else  if(lineNumber.equals("THAT") || lineNumber.equals("R4"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"4");
                        }
                        else  if(lineNumber.equals("R5"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"5");
                        }
                        else  if(lineNumber.equals("R6"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"6");
                        }
                        else  if(lineNumber.equals("R7"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"7");
                        }
                        else  if(lineNumber.equals("R8"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"8");
                        }
                        else  if(lineNumber.equals("R9"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"9");
                        }
                        else  if(lineNumber.equals("R10"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"10");
                        }
                        else  if(lineNumber.equals("R11"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"11");
                        }
                        else  if(lineNumber.equals("R12"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"12");
                        }
                        else  if(lineNumber.equals("R13"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"13");
                        }
                        else  if(lineNumber.equals("R14"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"14");
                        }
                        else  if(lineNumber.equals("R15"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"15");
                        }
                        else  if(lineNumber.equals("SCREEN"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"16384");
                        }
                        else  if(lineNumber.equals("KBD"))
                        {
                            FILE=FILE.replaceAll(lineNumber,"24576");
                        }
                        else if (counter<16384)
                        {
                        		//found a variable
                        		if(!symbolTable.containsKey(line))
                        		{
                        			//System.out.println(line);
                        			symbolTable.put(line, counter);
                        			String rep = "@"+counter+"\n";
                        			FILE=FILE.replace(line+"\n", rep);
                        			System.out.println(line + " | " + rep);
                        			counter++;
                        		}
                        		else
                        		{
                        			int existingCount = symbolTable.get(line);
                        			System.out.println(line);
                        			FILE=FILE.replace(line+"\n","@"+existingCount+"\n");
                        		}
	                        //count=Integer.toString(counter);
	                        //FILE=FILE.replace(line+"\n","@"+count+"\n");
	                        
                        }
                    }
                }
            }
       FILE=FILE.replaceAll("\\(","");
       FILE=FILE.replaceAll("\\)","");
         
       //reads through the string FILE and converts assembly to machine language
 
            Scanner scan3 = new Scanner(FILE);
             
        while(scan3.hasNext())
        {
            line=scan3.nextLine();
            c=line.charAt(0);
            q=line.charAt(1);
            Length=line.length();
            
          if(c =='@')
        {
            lineNumber=line.replace("@","");
           
            y= Integer.parseInt(lineNumber);
           
            
           //A bits
           if(y<=32767)
          {            
               
            check=y-32768;
           if(check>=0)
           {
               A[0]=1;
               y=y-32768;
            }
           else
           {
                A[0]=0;
            }
         
            check=y-16384;
           if(check>=0)
           {
               A[1]=1;
               y=y-16384;
            }
            else
           {
                A[1]=0;
            }
          
            check=y-8192;
           if(check>=0)
           {
               A[2]=1;
               y=y-8192;
            }
            else
           {
                A[2]=0;
            }
          
            check=y-4096;
           if(check>=0)
           {
               A[3]=1;
               y=y-4096;
            }
            else
           {
                A[3]=0;
            }
          
            check=y-2048;
           if(check>=0)
           {
               A[4]=1;
               y=y-2048;
            }
            else
           {
                A[4]=0;
            }
          
            check=y-1024;
           if(check>=0)
           {
               A[5]=1;
               y=y-1024;
            }
            else
           {
                A[5]=0;
            }
           
            check=y-512;
           if(check>=0)
           {
               A[6]=1;
               y=y-512;
            }
            else
           {
                A[6]=0;
            }
           
            check=y-256;
           if(check>=0)
           {
               A[7]=1;
               y=y-256;
            }
            else
           {
                A[7]=0;
            }
           
            check=y-128;
           if(check>=0)
           {
               A[8]=1;
               y=y-128;
            }
            else
           {
                A[8]=0;
            }
           
            check=y-64;
           if(check>=0)
           {
               A[9]=1;
               y=y-64;
            }
            else
           {
                A[9]=0;
            } 
           
            check=y-32;
           if(check>=0)
           {
               A[10]=1;
               y=y-32;
            }
            else
           {
                A[10]=0;
            } 
           
            check=y-16;
           if(check>=0)
           {
               A[11]=1;
               y=y-16;
            }
            else
           {
                A[11]=0;
            }
           
            check=y-8;
           if(check>=0)
           {
               A[12]=1;
               y=y-8;
            }
            else
           {
                A[12]=0;
            } 
           
            check=y-4;
           if(check>=0)
           {
               A[13]=1;
               y=y-4;
            }
            else
           {
                A[13]=0;
            }
           
            check=y-2;
           if(check>=0)
           {
               A[14]=1;
               y=y-2;
            }
            else
           {
                A[14]=0;
            }
           
            check=y-1;
           if(check>=0)
           {
               A[15]=1;
               y=y-1;
            }
            else
           {
                A[15]=0;
            }
            
            
           for(int I=0; I<16; I++)
           {
               fw.write(A[I]+"");
                             
            }
           
          }
 
         }
                  
        
        
         //destination bits + comp bits following =
       
            P=line.indexOf("=");
       
            if(P>0)
            {
                lineNumber=line.substring(0,P);
                Length2=lineNumber.length();
                for(int L=0; L<Length2; L++)
                {
                  Z=lineNumber.charAt(L);
                  if(Z=='D')
                  {
                      C[11]=1;
                    }
                  else if(Z=='A')
                  {
                      C[10]=1;
                    }
                  else if(Z=='M')
                  {
                      C[12]=1;
                    }
              }
            
            
             comp=line.substring(P+1,Length);
             
             if(comp.equals("0"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=0;
                C[6]=1;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("-1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=1;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("A"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("!D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("!A"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("-D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("-A"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D+1"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=1;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("A+1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D-1"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("A-1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D+A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D-A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("A-D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D&A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("D|A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("M"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("!M"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("-M"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("M+1"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("M-1"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D+M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D-M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("M-D"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D&M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("D|M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=0;
                C[9]=1;
            }
             for(int I=0; I<16; I++)
            {
               fw.write(C[I]+"");
               C[I]=0;
            }
              C[0]=1;
              C[1]=1;
              C[2]=1;
           }
           
        
        
        
        
        
            
            
            
            
            //Jump bits and comp bits before ';'
           
         T=line.indexOf(";");
        if(T>0)
            {
                comp=line.substring(0,T);
                JUMP=line.substring(T+1,Length);
               
                if(JUMP.equals("JGT"))
                {
                    C[13]=0;
                    C[14]=0;
                    C[15]=1;
                }
                else if(JUMP.equals("JEQ"))
                {
                    C[13]=0;
                    C[14]=1;
                    C[15]=0;
                }
                else if(JUMP.equals("JGE"))
                {
                    C[13]=0;
                    C[14]=1;
                    C[15]=1;
                }
                else if(JUMP.equals("JLT"))
                {
                    C[13]=1;
                    C[14]=0;
                    C[15]=0;
                }
                else if(JUMP.equals("JNE"))
                {
                    C[13]=1;
                    C[14]=0;
                    C[15]=1;
                }
                else if(JUMP.equals("JLE"))
                {
                    C[13]=1;
                    C[14]=1;
                    C[15]=0;
                }
                else if(JUMP.equals("JMP"))
                {
                    C[13]=1;
                    C[14]=1;
                    C[15]=1;
                }
               
                 if(comp.equals("0"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=0;
                C[6]=1;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("-1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=1;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("A"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("!D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("!A"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("-D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("-A"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D+1"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=1;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("A+1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D-1"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=1;
                C[7]=1;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("A-1"))
            {
                C[3]=0;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D+A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D-A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("A-D"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D&A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("D|A"))
            {
                C[3]=0;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("M"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("!M"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=1;
            }
            else if(comp.equals("-M"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("M+1"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("M-1"))
            {
                C[3]=1;
               
                C[4]=1;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D+M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=0;
            }
            else if(comp.equals("D-M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=0;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("M-D"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=1;
                C[8]=1;
                C[9]=1;
            }
            else if(comp.equals("D&M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=0;
                C[6]=0;
                C[7]=0;
                C[8]=0;
                C[9]=0;
            }
            else if(comp.equals("D|M"))
            {
                C[3]=1;
               
                C[4]=0;
                C[5]=1;
                C[6]=0;
                C[7]=1;
                C[8]=0;
                C[9]=1;
            }
             for(int I=0; I<16; I++)
           {
               fw.write(C[I]+"");
               C[I]=0;
            }
              C[0]=1;
              C[1]=1;
              C[2]=1;
           
            }
          
           fw.write("\n");   
        
     }
      fw.close();
     
    }
}
 