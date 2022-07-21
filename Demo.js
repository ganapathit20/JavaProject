//**Starting js basic */

var http = require('http');

http.createServer(function(req, res){
    res.writeHead(200, {'Content-Type': 'text/html'});
    console.log("Done....")
    res.write("connect")
    res.end();
}).listen(8088);

//Modules create and use different aear

var cal = require("./cal.js")
// add
var result = cal.addition(10,34)
console.log(result)

// sub
var result = cal.substraction(100,34)
console.log(result)

// mul
var result = cal.multiplication(100,34)
console.log(result)

// File System Example

var fs = require('fs');

//readFile 3 args needs 1.filePath, 2. formate, 3. callback function
//callback function needs 2 args 1. error, 2. data(give any name)
// callback funcrion use 2ways 1. function(){}, 2. ()=>{}
fs.readFile("cal.js", "UTF8", (error, data)=>{ 

    if(error)
        throw error
    console.info(data)

})

//write files 
//it is create new file if does not exited 
//readFile 3 args needs 1.filePath, 2. content for the file what you write, 3. callback function
//callback function needs 2 args 1. error, 2. data(give any name)
// callback funcrion use 2ways 1. function(){}, 2. ()=>{}
fs.writeFile("cal1.js", "console.info(\"new File created\")", function(error){
    if(error)
    throw error

    console.info("create done...")

})


//it is append data to the same file if there
fs.appendFile("cal.js", "console.info(\"hi\")", function(error){
    if(error)
    throw error

    console.info("append done...")

})

//it is delete data  from file 
fs.unlink("cal1.js", function(error){
    if(error)
     throw error

    console.info("deleted done...")

})