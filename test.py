

'''
Created on Fab 24, 2018

@author: adi tayri
'''


import os
import io
import random

import plotly
import plotly.plotly as py
import plotly.graph_objs as go
import subprocess
plotly.tools.set_credentials_file(username='adita', api_key='T5ZzZ3SsaTXrHcAVwbJU')
from subprocess import STDOUT,PIPE

def compile_java(java_file):
    subprocess.check_call(['javac', java_file])

def execute_java(java_file, stdin):
    java_class,ext = os.path.splitext(java_file)
    cmd = ['java', java_class]
    proc = subprocess.Popen(cmd, stdin=PIPE, stdout=PIPE, stderr=STDOUT)
    stdout,stderr = proc.communicate(stdin)
    print ('This was "' + stdout + '"')

#compile_java('SplittingSet.java')
# execute_java('SplittingSet', 'CnfFile.txt')





PATH_TO_SPLITTING_SET="/home/rachel/Desktop/Minimal-Splitting-Set/bin"
path="/home/rachel/eclipse-workspace/Project"
# def RandomCNF(L , M , K):
#     #cnf=[]
#     directory="/"+str(M)+"variables"
#     filename="cnf_"+str(L)+"_"+str(M)
#     filepath=os.path.join(path+directory,filename)
#     myFile=io.FileIO(filepath, "w") 
#     myFile.write(str(L)+"\n")
#     for i in range(0,L):  
#         line=""
#         for j in range(0, K):
#             var=random.randint(1,M)
#             if(random.getrandbits(1)):
#                 var=var*-1        
#             line += str(var)+" "
#         line+="0" 
#         myFile.write(line+"\n")

def RandomCNF(L , M , K):
    filename="cnf_"+str(L)+"_"+str(M)
    filepath=os.path.join(path,filename)
    myFile=io.FileIO(filepath, "w") 
    myFile.write(str(L)+"\n")
    for i in range(0,L):  
        line=""
        for j in range(0, K):
            var=random.randint(1,M)
            if(random.getrandbits(1)):
                var=var*-1        
            line += str(var)+" "
        line+="0" 
        myFile.write(line+"\n")
def RandomPositiveCNF(L , M , K):
    #cnf=[]
    directory="/"+str(M)+"variables"
    filename="cnf_"+str(L)+"_"+str(M)
    filepath=os.path.join(path+directory,filename)
    myFile=io.FileIO(filepath, "w") 
    myFile.write(str(L)+"\n")
    for i in range(0,L):  
        line=""
        allNegative=True
        for j in range(0, K):
            var=random.randint(1,M)
            if(j!=K-1 or not allNegative):
                if(random.getrandbits(1)):
                    var=var*-1    
            if(var>0):
                allNegative=False            
            line += str(var)+" "
        line+="0" 
        myFile.write(line+"\n")




#RandomPositiveCNF(160, 40, 3)


def getJavaOutput(filepath,filename):
    command = "java -classpath "+PATH_TO_SPLITTING_SET+" Main "+filepath+"/"+filename
    return os.popen(command).read()

    
   

#print(getJavaOutput("/home/rachel/Desktop/Minimal-Splitting-Set", "CnfFile.txt"))
    
def frange(start, stop, step):
    i = start
    while i < stop:
        yield i
        i += step
        
 
        
def FindMedian(arr):
    median=0.0
    size = len(arr)  
    if size == 0:
        return
    arr.sort(cmp=None, key=None, reverse=False) 
    if size%2 == 0:
        num1=float(arr[(size-2)/2])
        num2=float(arr[size/2])
        median=(num1+num2)/2
    else:
        middle=(size-1)/2
        median = arr[middle] 
        
    return median

def FindAVG(arr):
    s=0.0
    size=len(arr)
    if size == 0:
        return
    for i in arr:
        fi=float(i)
        s=s+fi
    avg=s/size
    return avg



def testMedian(): 
    K = 3 #K-SAT
    variableList=[20,40,50]
    data=[]
    for M in variableList:
        directory="/"+str(M)+"variables"
        filepath=os.path.join(path+directory)
        SplittingSetSizeList=[]
        MedianList=[]
        AxisX=[]
        ratio=2
        for ratio in frange(2,9,0.2):
            AxisX.append(ratio)
            L = int(ratio*M)
            for i in range(0,100):
                RandomPositiveCNF(L, M, K)
                filename="cnf_"+str(L)+"_"+str(M)
                size=getJavaOutput(filepath, filename)
                SplittingSetSizeList.append(size)
                os.remove(os.path.join(filepath,filename))
            median=FindMedian(SplittingSetSizeList)  
            MedianList.append(median)   
            del SplittingSetSizeList[:] 
        trace = go.Scatter(
            x = AxisX,
            y = MedianList,
            name = str(M)+"variabls",
            line = dict(
                color = ("rgb("+str(random.randint(1,256))+","+str(random.randint(1,256))+","+str(random.randint(1,256))+")"),
                width = 4)
            )
        data.append(trace)   
    layout = dict(title = 'tets median',
              xaxis = dict(title = 'Rules and variables ratio'),
              yaxis = dict(title = 'Median Splitting Set Size'),
              )
 
    fig = dict(data=data, layout=layout)
    py.plot(fig, filename='medianSplittingSetSize')        
   
   
  
def testEverage(): 
    K = 3 #K-SAT
    variableList=[20,40,50]
    data=[]
    for M in variableList:
        directory="/"+str(M)+"variables"
        filepath=os.path.join(path+directory)
        AverageList=[]
        AxisX=[]
        ratio=2
        for ratio in frange(2,10,0.2):
            AxisX.append(ratio)
            SplittingSetSizeList=[]
            L = int(ratio*M)
            for i in range(0,100):
                RandomPositiveCNF(L, M, K)
                filename="cnf_"+str(L)+"_"+str(M)
                size=getJavaOutput(filepath,filename)
                SplittingSetSizeList.append(size)
                os.remove(os.path.join(filepath,filename))
            avg=FindAVG(SplittingSetSizeList)  
            AverageList.append(avg)   
        trace = go.Scatter(
            x = AxisX,
            y = AverageList,
            name = str(M)+"variables",
            line = dict(
                color = ("rgb("+str(random.randint(1,256))+","+str(random.randint(1,256))+","+str(random.randint(1,256))+")"),
                width = 4)
            )
        data.append(trace)   
    layout = dict(title = 'test average',
              xaxis = dict(title = 'Rules and variables ratio'),
              yaxis = dict(title = 'Average Splitting Set Size'),
              )
 
    fig = dict(data=data, layout=layout)
    py.plot(fig, filename='averageSplittingSetSize')
    

def avgSplittingSet():
    K=3
    M=100
    avgSourceSize=[]
    AxisX=[]
    for ratio in frange(2,10,0.2):
        sourceSize=[]
        AxisX.append(ratio)
        L = int(M*ratio)
        for i in range(0,200): 
            RandomCNF(L, M, K)
            filename="cnf_"+str(L)+"_"+str(M)
            output=getJavaOutput(path, filename)
            sourceSize.append(output)
            os.remove(os.path.join(path,filename))
        avgSourceSize.append(FindAVG(sourceSize)) 
    trace1=go.Scatter(
        x=AxisX,
        y=avgSourceSize,
        name="Average run time",
        line=dict(
            color=('rgb(0,0,0)'),
            width=4,
           # dash='dash'
            )
        )
   
    data=[trace1]
    layout = dict(title = 'Average run time of computing Splitting set '+str(M)+" variables",
            xaxis = dict(title = 'Rules and variables ratio'),
            yaxis = dict(title = 'Average run time '),
            )
    fig = dict(data=data, layout=layout)  
    py.plot(fig, filename='Splitting set run time')   
     
avgSplittingSet()
#testMedian()   
#testEverage() 
#WHAT TO MESSURE? *SIZE OF SPLITTING SET, AVG,MEDIAN *
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
