var xacmlSpec = (function () {
    var dataTypeArray = [
        {value: "http://www.w3.org/2001/XMLSchema#string", caption: "字符串"},
        {value: "http://www.w3.org/2001/XMLSchema#boolean", caption: "布尔型"},
        {value: "http://www.w3.org/2001/XMLSchema#integer", caption: "整型"},
        {value: "http://www.w3.org/2001/XMLSchema#double", caption: "浮点型"},
        {value: "http://www.w3.org/2001/XMLSchema#time", caption: "时间"},
        {value: "http://www.w3.org/2001/XMLSchema#date", caption: "日期"},
        {value: "http://www.w3.org/2001/XMLSchema#dateTime", caption: "时间与日期"},
        {value: "http://www.w3.org/2001/XMLSchema#anyURI", caption: "uri"}
    ];
    var ruleCombiningAlgIds = [
        {value: "urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable", caption: "first-applicable"},
        {value: "urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-overrides", caption: "deny-overrides"},
        {value: "urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:permit-overrides", caption: "permit-overrides"},
        {value: "urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:ordered-deny-overrides", caption: "ordered-deny-overrides"},
        {value: "urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:ordered-permit-overrides", caption: "ordered-permit-overrides"},
        {value: "urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-unless-permit", caption: "deny-unless-permit"},
        {value: "urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:permit-unless-deny", caption: "permit-unless-deny"},
        {value: "urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides", caption: "deny-overrides"},
        {value: "urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides", caption: "permit-overrides"},
        {value: "urn:oasis:names:tc:xacml:1.1:rule-combining-algorithm:ordered-deny-overrides", caption: "ordered-deny-overrides"},
        {value: "urn:oasis:names:tc:xacml:1.1:rule-combining-algorithm:ordered-permit-overrides", caption: "ordered-permit-overrides"}
    ];
    var functionIds = [
        {value: "urn:oasis:names:tc:xacml:1.0:function:string-equal", caption: "字符串相等"},
        {value: "urn:oasis:names:tc:xacml:1.0:function:string-regexp-match", caption: "字符串正則匹配"},
        {value: "urn:oasis:names:tc:xacml:1.0:function:string-bag", caption: "字符串集合"},
        {value: "urn:oasis:names:tc:xacml:1.0:function:and", caption: "逻辑与"},
        {value: "urn:oasis:names:tc:xacml:1.0:function:or", caption: "逻辑或"},
        {value: "urn:oasis:names:tc:xacml:1.0:function:not", caption: "逻辑非"}
    ];
    var matchElement = "<Match MatchId=\"matchId\"><AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\"></AttributeValue><AttributeDesignator Category=\"\" AttributeId=\"\" DataType=\"\" MustBePresent=\"false\"/></Match>";

    return {
        onchange: function(){
            console.log("I been changed now!")
        },
        validate: function(obj){
            console.log("I be validatin' now!")
        },
        elements: {
            "Policy": {
                menu: [
                    {
                        caption: "添加 <Description>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Description></Description>",
                        hideIf: function(jsElement){
                            return jsElement.hasChildElement("Description");
                        }
                    },
                    {
                        caption: "添加 <Rule>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Rule RuleId=\"ruleId\" Effect=\"Permit\"/>"
                    },
                    {
                        caption: "添加 @PolicyId=\"something\"",
                        action: Xonomy.newAttribute,
                        actionParameter: {name: "PolicyId", value: "policy01"},
                        hideIf: function(jsElement){
                            return jsElement.hasAttribute("PolicyId");
                        }
                    },
                    {
                        caption: "添加 @RuleCombiningAlgId=\"something\"",
                        action: Xonomy.newAttribute,
                        actionParameter: {name: "RuleCombiningAlgId", value: "algId"},
                        hideIf: function(jsElement){
                            return jsElement.hasAttribute("RuleCombiningAlgId");
                        }
                    }
                ],
                attributes: {
                    "PolicyId": {
                        asker: Xonomy.askString
                    },
                    "RuleCombiningAlgId": {
                        asker: Xonomy.askPicklist,
                        askerParameter : ruleCombiningAlgIds
                    }
                }
            },
            "Description": {
                hasText: true,
                oneliner: true,
                mustBeBefore: ["Target"]
            },
            "Target": {
                mustBeBefore: ["Condition"],
                menu: [
                    {
                        caption: "添加一组<Match>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<AnyOf><AllOf>"+ matchElement +"</AllOf></AnyOf>"
                    },
                    {
                        caption: "刪除当前 <Target>",
                        action: Xonomy.deleteElement,
                        hideIf: function(jsElement){
                            return 'Policy' == jsElement.parent().name;
                        }
                    }
                ]
            },
            "AnyOf": {
                menu: [
                    {
                        caption: "添加一组<Match>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<AllOf>"+ matchElement +"</AllOf>"
                    },
                    {
                        caption: "刪除当前 <AnyOf>",
                        action: Xonomy.deleteElement
                    }
                ]
            },
            "AllOf": {
                menu: [
                    {
                        caption: "添加一组<Match>",
                        action: Xonomy.newElementChild,
                        actionParameter: matchElement
                    }
                ]
            },
            "Match": {
                attributes: {
                    "MatchId": {
                        asker: Xonomy.askString
                    }
                }
            },
            "AttributeValue": {
                hasText: true,
                oneliner: true,
                attributes: {
                    "DataType": {
                        asker: Xonomy.askPicklist,
                        askerParameter : dataTypeArray
                    }
                },
                menu: [
                    {
                        caption: "刪除当前 <AttributeValue>",
                        action: Xonomy.deleteElement,
                        hideIf: function(jsElement){
                            return 'Match' == jsElement.parent().name;
                        }
                    }
                ]
            },
            "AttributeDesignator": {
                attributes: {
                    "Category": {
                        asker: Xonomy.askString
                    },
                    "AttributeId": {
                        asker: Xonomy.askString
                    },
                    "DataType": {
                        asker: Xonomy.askPicklist,
                        askerParameter : dataTypeArray
                    },
                    "MustBePresent": {
                        asker: Xonomy.askPicklist,
                        askerParameter : ["true", "false"]
                    }
                },
                menu: [
                    {
                        caption: "刪除当前 <AttributeDesignator>",
                        action: Xonomy.deleteElement,
                        hideIf: function(jsElement){
                            return 'Match' == jsElement.parent().name;
                        }
                    }
                ]
            },
            "Rule": {
                menu: [
                    {
                        caption: "添加 <Description>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Description></Description>",
                        hideIf: function(jsElement){
                            return jsElement.hasChildElement("Description");
                        }
                    },
                    {
                        caption: "添加 <Condition>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Condition></Condition>",
                        hideIf: function(jsElement){
                            return jsElement.hasChildElement("Condition");
                        }
                    },
                    {
                        caption: "添加 <Target>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Target><AnyOf><AllOf>"+ matchElement +"</AllOf></AnyOf></Target>",
                        hideIf: function(jsElement){
                            return jsElement.hasChildElement("Target");
                        }
                    },
                    {
                        caption: "刪除当前 <Rule>",
                        action: Xonomy.deleteElement
                    }
                ],
                attributes: {
                    "Effect": {
                        asker: Xonomy.askPicklist,
                        askerParameter : [
                            {value: "Permit", caption: "允许"},
                            {value: "Deny", caption: "拒绝"}
                        ]
                    },
                    "RuleId": {
                        asker: Xonomy.askString
                    }
                }
            },
            "Condition": {
                menu: [
                    {
                        caption: "添加 <Apply>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Apply FunctionId=\"\"></Apply>"
                    },
                    {
                        caption: "刪除当前 <Condition>",
                        action: Xonomy.deleteElement
                    }
                ],
                mustBeAfter: ["Target"]
            },
            "Apply": {
                menu: [
                    {
                        caption: "添加 <Apply>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Apply FunctionId=\"\"></Apply>"
                    },
                    {
                        caption: "添加 <Function>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<Function FunctionId=''></Function>"
                    },
                    {
                        caption: "添加 <AttributeDesignator>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<AttributeDesignator Category=\"\" AttributeId=\"\" DataType=\"\" MustBePresent=\"false\"/>"
                    },
                    {
                        caption: "添加 <AttributeValue>",
                        action: Xonomy.newElementChild,
                        actionParameter: "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\"></AttributeValue>"
                    },
                    {
                        caption: "刪除当前 <Apply>",
                        action: Xonomy.deleteElement
                    }
                ],
                attributes: {
                    "FunctionId": {
                        asker: Xonomy.askPicklist,
                        askerParameter : functionIds
                    }
                }
            },
            "Function": {
                menu:[
                    {
                        caption: "刪除当前 <Function>",
                        action: Xonomy.deleteElement
                    }
                ],
                attributes: {
                    "FunctionId": {
                        asker: Xonomy.askPicklist,
                        askerParameter : functionIds
                    }
                }
            }
        }
    }
})();