Ext.define('Ext.ux.DateTimeSelector', {
  extend: 'Ext.window.Window',
  width: 400,
  resizable: false,
  layout: 'fit',
  initComponent: function() {
    var me = this;

    var initialDate = me.initialTimestamp ? new Date(me.initialTimestamp) : new Date();
    var initialSliderValue = initialDate.getHours() * 4 + Math.floor(initialDate.getMinutes() / 15);

    Ext.apply(me, {
      setTime: function(timestamp) {
        if(timestamp) {
          var date = new Date(timestamp);
          var sliderValue = date.getHours() * 4 + Math.floor(date.getMinutes() / 15);
          me.down('datefield').setValue(date);
          me.down('timefield').setValue(date);
          me.down('slider').setValue(sliderValue);
        }
      },
      buttons: [
        {
          xtype: 'checkbox',
          fieldLabel: 'Close after select',
          labelWidth: 110,
          checked: true
        },
        '->',
        {
          text: 'Select',
          handler: function() {
            var form = me.down('form');
            if(form.isValid()) {
              var date = form.down('datefield').getValue(false);
              var time = form.down('timefield').getValue(false);
              var timestamp = date.getTime() + time.getHours() * 60 * 60 * 1000 + time.getMinutes() * 60 * 1000;
              if(me.onSelect && typeof me.onSelect == 'function') {
                me.onSelect(timestamp);
              }
              if(me.down('checkbox').getValue(false)) me.close();
            }
          }
        },
        {
          text: 'Close',
          handler: function() {
            me.close();
          }
        }
      ],
      items: [
        {
          xtype: 'form',
          border: false,
          bodyPadding: 10,
          fieldDefaults: {
            validateOnBlur: false,
            validateOnChange: false
          },
          items:[
            {
              xtype: 'datefield',
              labelWidth: 50,
              fieldLabel: 'Date',
              value: initialDate,
              fieldStyle: 'text-align: center',
              format:'j M Y',
              dateFormat: 'time',
              width: 150
            },
            {
              xtype: 'container',
              layout: 'hbox',
              items: [
                {
                  xtype: 'timefield',
                  fieldLabel: 'Time',
                  labelWidth: 50,
                  width: 140,
                  format: 'H:i',
                  fieldStyle: 'text-align: center',
                  value: initialDate,
                  listeners: {
                    change: function(timefield, newValue) {
                      if(newValue) {
                        var slider = me.down('slider');
                        slider.suspendEvents(false);
                        slider.setValue(newValue.getHours() * 4 + Math.floor(newValue.getMinutes() / 15));
                        slider.resumeEvents();
                      }
                    }
                  }
                },
                {
                  xtype: 'slider',
                  flex: 1,
                  margin: '2 5 0 5',
                  minValue: 0,
                  maxValue: 95,
                  increment: 1,
                  value: initialSliderValue,
                  useTips: false,
                  convertValueToTime: function(value) {
                    return {hours: Math.floor(value / 4), minutes: value % 4}
                  },
                  getTimeString: function(value) {
                    var time = this.convertValueToTime(value);
                    return (time.hours < 10 ? '0' : '') + time.hours + ':' + (time.minutes ? '' : '0') + (time.minutes * 15);
                  },
                  listeners: {
                    change: function(slider, newValue) {
                      me.down('timefield').setValue(this.getTimeString(newValue));
                    }
                  }
                }
              ]
            }
          ]
        }
      ]
    });

    me.callParent(arguments);
  }

});