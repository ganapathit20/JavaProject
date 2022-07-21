const { clear } = require('console')
const express = require('express')

const start = express()

start.get("/", function(req, res){
   // res.json({"Msg":"Wlcome to NodeJs"})
    res.send("Wlcome to NodeJs")
   // res.sendStatus(200, "It's Working Fine")
})

//Two type to get id 1. params, 2. query example below
start.get("/data", (req,res)=> { //start.get("/data:id", (req,res)=> { 

    const id = req.query.id      //  const id = req.params.id
    res.json({
        "Course":"NodeJs",
        "id":id
    })

})

start.listen(9000,  (req, res) => {
    console.log("Running....")
})