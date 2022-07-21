function addition(fValue, sValue){
    return fValue + sValue;
}

module.exports.addition = addition;// type 1 for moudle export

function substraction(fValue, sValue){
    return fValue - sValue;
}

exports.substraction = substraction; // type 2 for moudle export

exports.multiplication = function substraction(fValue, sValue){ // type 3 for moudle export
    return fValue * sValue;
}
