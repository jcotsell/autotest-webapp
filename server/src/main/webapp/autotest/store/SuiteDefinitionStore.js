    Ext.define('AutoTest.store.SuiteDefinitionStore', {
  extend: 'Ext.data.Store',
  model: 'seb.autotest.server.models.SuiteDefinition',
  autoLoad: false,
  proxy: {
                  type: 'direct',
                  directFn: suiteService.getSuites
              }
  });