
# Matillion Technical Test

## Test1
```
    // This method counts and returns the number of different characters between two strings
    // of the same length
    private static int countUniqueCharacters(String stringOne, String stringTwo){
      int uniqueChars = 0;
      for(int i = 0; i < stringOne.length(); i++){
        if(stringOne.charAt(i) != stringTwo.charAt(i)){
          uniqueChars++;
        }
      }
      return uniqueChars;
    }
```

## Test 2

```
mvn clean install
java -jar target/matillion-0.1.jar
```
