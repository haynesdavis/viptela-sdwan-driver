# CP4NA Resource Package

## Resource Package Structure

Resource packages for CP4NA should contain (at a minimum) the following content for the viptela-sdwan-driver to work correctly.

```
helloworld.zip
+--- Definitions
|    +--- lm
|         +--- resource.yaml
+--- Docs
|    +--- Readme.md
+--- Lifecycle
     +--- lifecycle.mf
     +--- viptela-sdwan
          +--- templates
               +--- AttachDevice.json             
```
The `resource.yaml` file is the resource descriptor used by CP4NA.

AttachDevice.json template file in the `lifecycle/viptela-sdwan/templates` directory is used to create payload for attaching a supplied templateId to an Edge on the Viptela orchestration.