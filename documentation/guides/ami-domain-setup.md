
## DNS setup with the domain provider

####
Let us refer to the three servers as edge server, james server and direct server.
And assume that we are interested in accessing them through these three domain names respectively:
edge.mydomain.com,  james.mydomain.com, direct.mydomain.com.

Since the Direct setup involves publishing X509 certificates and other DNS record management, using the name server
bundled with the direct installation (on edge and direct servers) is recommended. Although it should be noted that this can 
be accomplished through an equivalent setup completely through your domain provider (Amazon Route 53, GoDaddy etc.); 
the provided approach works by delegating the NS/glue entries to the already bundled name service, 
providing an easy way to manage the entries within the server 
(and not having to modify anything at the domain provider after the initial setup).

For this setup to work, these records needed to be added in your domain provider setup:



##edge server:
#####A records (glue records)
```
ns1.edge.mydomain.com         A        ip-of-the-edge-server
ns2.edge.mydomain.com         A        ip-of-the-edge-server
```

#####NS records 
```
edge.mydomain.com NS  ns1.edge.mydomain.com
edge.mydomain.com NS ns2.edge.mydomain.com
```

##james server:
#####A record
```
james.mydomain.com  A  ip-of-the-james-server
```
#####MX record
```
james.mydomain.com   MX james.mydomain.com
```


##direct server:
#####A records (glue records)
```
ns1.direct.mydomain.com      A       ip-of-the-direct-server
ns2.direct.mydomain.com      A       ip-of-the-direct-server
```

#####NS records 
```
direct.mydomain.com NS  ns1.direct.mydomain.com
direct.mydomain.com NS ns2.direct.mydomain.com
```

### NOTE
Please note that after the glue record setup above, in order for the edge.mydomain.com/direct.mydomain.com to resolve properly,
the name servers in those instances have to be configured and running (providing the A records for name resolution) - which are detailed in the steps below. You may have to use their ip addresses in the steps 1/2 below (you could temporarily enable A records directly from the domain provider, but have to delete them and test after the completion however):

#### Steps to configure the domains within the servers:

1. Setting up the edge domain using http://edge.mydomain.com:11080/config-ui
2. Setting up the direct domain using http://direct.domain.com:8081/config-ui
3. Creating these domains in james application bundled in: james.mydomain.com (/opt/james) and direct.mydomain.com (/opt/direct/james)
4. Configuring the application.properties in the edge.mydomain.com (/opt/ttp/application.properties) with the above domains.

