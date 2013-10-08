Ext.define('AutoTest.view.PanelHeader', {
  extend: 'Ext.Panel', // user toolbar because we prefer toolbar's styling
  alias: 'widget.headerpanel'
  });

Ext.define('AutoTest.view.SearchPanel', {
  extend: 'Ext.Panel', // user toolbar because we prefer toolbar's styling
  alias: 'widget.searchpanel'
  });

  Ext.define('AutoTest.view.MainPanel', {
    extend: 'Ext.Panel', // user toolbar because we prefer toolbar's styling
    alias: 'widget.mainpanel'
    });


Ext.define('AutoTest.view.Viewport', {
  extend: 'Ext.container.Viewport',
  layout: 'border',
  items: [
    {xtype: 'headerpanel', region: 'north'},
    {xtype: 'searchpanel', region: 'west'} ,
    {xtype: 'mainpanel', region: 'center'}
  ]
});