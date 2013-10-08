Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);

Ext.state.Manager.setProvider(Ext.create('Ext.state.CookieProvider', {
  expires: new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 30)) //30 days
}));

Ext.Error.handle = function (err) {
    alert(err.msg);
};

Ext.application({
    stores: [
    'SuiteDefinitionStore'
  ],
  model: [
    'SuiteDefinitionModel','TestRunModel','TestResultModel'
  ],
  name: 'AutoTest'
});

