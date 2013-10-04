Ext.define('Ext.ux.CopyButton', {
  extend: 'Ext.button.Button',
  xtype: 'copybutton',
  icon: '../resources/images/clipboard.png',
  text: 'Copy to clipboard',
  listeners: {
    // We don't need to use the ZeroClipboard library for IE as it can do clipboard copying for us.
    afterrender: function() {
      var me = this;
      if(!window.clipboardData) {
        var clipboard = new ZeroClipboard(me.getEl().dom);
        clipboard.handlers = {};
        clipboard.on('mouseover', function() {
          clipboard.setText(me.copyValue);
        });
        clipboard.on('complete', function() {
          var parentWindow = me.up('window');
          if(parentWindow && me.closeWindowOnCopy) parentWindow.close();
        });
      }
    },
    focus: function() {
      this.blur();
    }
  },
  handler: function() {
    if(window.clipboardData) {
      window.clipboardData.setData("Text", this.copyValue);
      var parentWindow = this.up('window');
      if(parentWindow && this.closeWindowOnCopy) parentWindow.close();
    }
  }
});