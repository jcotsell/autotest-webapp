<html>

<head>
  <title>AutoTest</title>
    <link rel="stylesheet" type="text/css" href="/resources/ext-all-access.css" />
               <script src="../ext-4/ext-all-dev.js" type="text/javascript"></script>
               <script src="/action/api.js" type="text/javascript"></script>
               <script src="app.js" type="text/javascript"></script>
               <script src="model/TestRunModel.js" type="text/javascript"></script>
               <script src="model/TestResultModel.js" type="text/javascript"></script>
               <script src="model/SuiteDefinitionModel.js" type="text/javascript"></script>
               <script src="store/SuiteDefinitionStore.js" type="text/javascript"></script>
   </head>


<body>
  <script type="text/javascript">
  Ext.require([
            'Ext.grid.*',
            'Ext.data.*',
            'Ext.panel.*',
            'Ext.layout.container.Border'
        ]);

        Ext.onReady(function(){
            var store = Ext.create("AutoTest.store.SuiteDefinitionStore");
            store.load();
            var grid = Ext.create('Ext.grid.Panel', {
            		store: store,
            		columns: [ {
            			header: 'Id',
            			dataIndex: 'id',
            			flex: 1,
            			editor: {
            				allowBlank: false
            			}
            		}, {
            			header: 'Name',
            			dataIndex: 'name',
            			flex: 1,
            			editor: {
            				allowBlank: false
            			}
            		}, {
            			header: 'lastUpdate',
            			dataIndex: 'lastUpdate',
            			flex: 1,
            			editor: {
            				allowBlank: true
            			}
            		} ],
            		renderTo: Ext.getBody(),
            		width: 1000,
            		height: 400,
            		title: 'Persons',
            		frame: true
            	});
        });


</script>
</body>
</html>