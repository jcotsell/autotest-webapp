// Fixes black boxes in chrome v27 - http://stackoverflow.com/questions/16922244/why-are-black-squares-appearing-in-chrome
Ext.override(Ext.menu.Menu, {
  hideMode: 'display'
});

// Exact copy of the method in the ExtJS source with one modification to fix a
// null error when trying to load the logs grid multiple times in a short period.
// If upgrading ExtJS in future double check that this override is still needed.
Ext.override(Ext.data.Store, {
  onPageMapClear: function() {
    var me = this,
      loadingFlag = me.wasLoading,
      reqs = me.pageRequests,
      req,
      page;

    if (me.data.events.pageadded) {
      me.data.events.pageadded.clearListeners();
    }

    me.loading = true;
    me.totalCount = 0;

    for (page in reqs) {
      if (reqs.hasOwnProperty(page)) {
        req = reqs[page];
        delete reqs[page];
        if(req) delete req.callback;  // This line used to be 'delete req.callback;'
      }
    }

    me.fireEvent('clear', me);

    me.loading = loadingFlag;
  }
});

// Fix small tabs in ext 4.2.1 accessibility theme.
Ext.override(Ext.tab.Tab, {
  height: 28
});

Ext.override(Ext.tab.Bar, {
  height: 32
});

//fix update button stays disabled in config/engines tab
Ext.override(Ext.grid.RowEditor,{

  addFieldsForColumn: function(column, initial) {
    var me = this,
      i,
      length, field;


    if (Ext.isArray(column)) {
      for (i = 0, length = column.length; i < length; i++) {
        me.addFieldsForColumn(column[i], initial);
      }
      return;
    }


    if (column.getEditor) {


      field = column.getEditor(null, {
        xtype: 'displayfield',
        getModelData: function() {
          return null;
        }
      });
      if (column.align === 'right') {
        field.fieldStyle = 'text-align:right';
      }


      if (column.xtype === 'actioncolumn') {
        field.fieldCls += ' ' + Ext.baseCSSPrefix + 'form-action-col-field';
      }


      if (me.isVisible() && me.context) {
        if (field.is('displayfield')) {
          me.renderColumnData(field, me.context.record, column);
        } else {
          field.suspendEvents();
          field.setValue(me.context.record.get(column.dataIndex));
          field.resumeEvents();
        }
      }
      if (column.hidden) {
        me.onColumnHide(column);
      } else if (column.rendered && !initial) {
        me.onColumnShow(column);
      }

      // -- start edit
      me.mon(field, 'change', me.onFieldChange, me);
      // -- end edit
    }
  }
});

// http://www.sencha.com/forum/showthread.php?267119-Button-click-keeps-focus
// In 4.2.1 buttons use the mouseover cls when focused which means they stay orange after clicking.
// We add this listener to remove focus after a click (focus event gets fired before a click event).
Ext.override(Ext.button.Button, {
  listeners: {
    click: function(button) {
      button.blur();
    }
  }
});