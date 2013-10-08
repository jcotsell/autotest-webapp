<html>

<head>
  <title>AutoTest</title>
    <link rel="stylesheet" type="text/css" href="/resources/ext-all-access.css" />
               <script src="../ext-4/ext-all-dev.js" type="text/javascript"></script>
               <script src="/action/api.js" type="text/javascript"></script>
               <script src="app.js" type="text/javascript"></script>
               <script src="model/TestRunModel.js" type="text/javascript"></script>
               <script src="model/TestResultModel.js" type="text/javascript"></script>
               <script src="view/ViewPort.js" type="text/javascript"></script>
               <script src="model/SuiteDefinitionModel.js" type="text/javascript"></script>
               <script src="store/SuiteDefinitionStore.js" type="text/javascript"></script>
   </head>


<body>
  <script type="text/javascript">
  Ext.require([
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.panel.*',
            'Ext.layout.container.Border',
            'AutoTest.view.Viewport'
        ]);

        Ext.onReady(function(){
            Ext.create("AutoTest.view.Viewport");
        });


</script>
</body>
</html>