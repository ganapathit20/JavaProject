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