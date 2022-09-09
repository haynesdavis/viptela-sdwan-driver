# Adding Viptela server configurations as Deployment Location in CP4NA

The deployment location for the target Viptela server can be added in the CP4NA UI, supplying the following information for infrastructure properties.

###### Example of JSON structure for Deployment Location
```jsonc
{
    "vManageHostFqdn": "http://viptela_host",    
    "vManageUser" : "Add vManageUser here",
    "vManagePassword" :  "Add vManagePassword here",
    "smartAccountUser" : "Add smartAccountUser here",
    "smartAccountPassword" : "Add smartAccountPassword here"
}
```