Ext.define("seb.autotest.server.models.TestResult",{extend:"Ext.data.Model",fields:[{name:"id",type:"int"},{name:"lastUpdate",type:"date"},{name:"className",type:"string"},{name:"methodName",type:"string"},{name:"fileName",type:"string"},{name:"resultCode",type:"int"},{name:"errorMessage",type:"string"}]});