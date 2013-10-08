Ext.define('AutoTest.view.ResultsPanel', {
        extend: 'Ext.grid.Panel', // user toolbar because we prefer toolbar's styling
        alias: 'widget.resultspanel'
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
        title: 'Results',
        frame: true
    });
