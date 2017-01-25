
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

###edge server:
#####A records (glue records)
ns1.edge.mydomain.com  ip-of-the-edge-server
ns2.edge.mydomain.com  ip-of-the-edge-server

#####NS records 
edge.mydomain.com NS  ns1.edge.mydomain.com
edge.mydomain.com NS ns2.edge.mydomain.com

###james server:
#####A record
james.mydomain.com  A  ip-of-the-james-server
#####MX record
james.mydomain.com   MX james.mydomain.com


###direct server:
#####A records (glue records)
ns1.direct.mydomain.com  ip-of-the-edge-server
ns2.direct.mydomain.com  ip-of-the-edge-server

#####NS records 
direct.mydomain.com NS  ns1.direct.mydomain.com
direct.mydomain.com NS ns2.direct.mydomain.com
