# Running the source code
The name of the main class is PortfolioRunner
In intellij go to Run/Debug Configurations and add gui or text as program arguments. This is required as based on this argument, the corresponding view is rendered
Add the required dependencies by going to File > Project Structure > Modules > Dependencies > Add > Jar or Directories > Choose the lib folder in the root folder

# Running the Jar File
On Windows
    - Execute the jar by typing java -cp 'Stocks.jar;lib\*' PortfolioRunner gui

On MACOS/Linux
    - Execute the jar by typing java -cp 'Stocks.jar:lib/*' PortfolioRunner gui

Note:
1) The valid command line arguments are text (or) gui. Based on the provided input, the view is rendered
2) The above command includes the lib folder in the classpath