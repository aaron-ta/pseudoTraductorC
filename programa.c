#include<stdio.lib>

void main(){ 

nxx=1	scanf("Cuantas calificaciones"%d,n);
	prom=0;
	i=0;
	
	while(i<n){
		scanf("Da una calificacion"%d,cal);
		prom=prom+cal;
	} 
	prom=prom/n;
	
	if (prom>5)	{ 
		printf("Aprobado con: ",prom);
	} 
	
	if (prom==10)	{ 
		printf("Excelente");
	} 
}