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

var result = cal.addition(10,34)

console.log(result)
