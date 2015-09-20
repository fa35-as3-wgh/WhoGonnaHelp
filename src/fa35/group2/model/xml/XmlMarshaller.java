package fa35.group2.model.xml;

import fa35.group2.model.FriendEntity;
import fa35.group2.model.PaymentEntity;
import fa35.group2.model.SkillEntity;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlMarshaller {

    private static class SchemaErrorHandler implements ErrorHandler {
        @Override
        public void warning(SAXParseException exception) throws SAXException {
            System.out.println("Warning: " + exception.getMessage() + " getColumnNumber is " +
                    exception.getColumnNumber() + " getLineNumber " + exception.getLineNumber() + " getPublicId " +
                    exception.getPublicId() + " getSystemId " + exception.getSystemId());
        }

        @Override
        public void error(SAXParseException exception) throws SAXException {
            throw new SAXException("Error: " + exception.getMessage() + " getColumnNumber is " +
                    exception.getColumnNumber() + " getLineNumber " + exception.getLineNumber() + " getPublicId " +
                    exception.getPublicId() + " getSystemId " + exception.getSystemId());
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException {
            throw new SAXException("Fatal Error: " + exception.getMessage() + " getColumnNumber is " +
                    exception.getColumnNumber() + " getLineNumber " + exception.getLineNumber() + " getPublicId " +
                    exception.getPublicId() + " getSystemId " + exception.getSystemId());
        }
    }

    private static DocumentBuilder documentBuilder;
    private static Transformer transformer;

    static {
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            Schema schema = factory.newSchema(XmlMarshaller.class.getResource("/fa35/group1/model/xml/schema.xsd"));

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setSchema(schema);
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            SchemaErrorHandler errorHandler = new SchemaErrorHandler();
            documentBuilder.setErrorHandler(errorHandler);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        } catch (ParserConfigurationException | TransformerConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Document document;
    private Map<Integer, PaymentEntity> paymentEntityMap;
    private Map<Integer, SkillEntity> skillEntityMap;
    private Map<Integer, FriendEntity> friendEntityMap;

    public XmlMarshaller(Map<Integer, PaymentEntity> paymentEntityMap,
                         Map<Integer, SkillEntity> skillEntityMap,
                         Map<Integer, FriendEntity> friendEntityMap) {
        this.paymentEntityMap = paymentEntityMap;
        this.skillEntityMap = skillEntityMap;
        this.friendEntityMap = friendEntityMap;
    }

    public void marshall() {
        this.document = documentBuilder.newDocument();

        Element root = this.document.createElement("persistence");
        Attr attr = this.document.createAttribute("name");
        attr.setValue("who_gonna_help");
        root.setAttributeNode(attr);

        root.appendChild(marshallPayments());
        root.appendChild(marshallSkills());
        root.appendChild(marshallFriend());

        this.document.appendChild(root);
    }

    public Element marshallPayments() {
        Element payments = this.document.createElement("payments");

        if (this.paymentEntityMap != null) {
            this.paymentEntityMap.forEach((integer, entity) ->
            {
                Element payment = this.document.createElement("payment");

                Attr attr = this.document.createAttribute("payment_id");
                attr.setValue(Integer.toString(entity.getId()));
                payment.setAttributeNode(attr);

                attr = this.document.createAttribute("name");
                attr.setValue(entity.getName());
                payment.setAttributeNode(attr);

                payments.appendChild(payment);
            });
        }

        return payments;
    }

    public Element marshallSkills() {
        Element skills = this.document.createElement("skills");

        if (this.skillEntityMap != null) {
            this.skillEntityMap.forEach((integer, entity) ->
            {
                Element skill = this.document.createElement("skill");

                Attr attr = this.document.createAttribute("skill_id");
                attr.setValue(Integer.toString(entity.getId()));
                skill.setAttributeNode(attr);

                attr = this.document.createAttribute("name");
                attr.setValue(entity.getName());
                skill.setAttributeNode(attr);

                skills.appendChild(skill);
            });
        }

        return skills;
    }

    public Element marshallFriend() {
        Element friends = this.document.createElement("friends");

        if (friendEntityMap != null) {
            friendEntityMap.forEach((integer, entity) ->
            {
                Element friend = this.document.createElement("friend");

                Attr attr = this.document.createAttribute("friend_id");
                attr.setValue(Integer.toString(entity.getId()));
                friend.setAttributeNode(attr);

                attr = this.document.createAttribute("name");
                attr.setValue(entity.getName());
                friend.setAttributeNode(attr);

                attr = this.document.createAttribute("contact");
                attr.setValue(entity.getContact());
                friend.setAttributeNode(attr);

                attr = this.document.createAttribute("note");
                attr.setValue(entity.getNote());
                friend.setAttributeNode(attr);

                friend.appendChild(marshallFriendPayments(entity.getPayments()));
                friend.appendChild(marshallFriendSkills(entity.getSkills()));

                friends.appendChild(friend);
            });
        }

        return friends;
    }

    private Element marshallFriendPayments(List<PaymentEntity> paymentEntities) {
        Element payments = this.document.createElement("payments");

        if (paymentEntities != null) {
            paymentEntities.forEach(entity ->
            {
                Element skill = this.document.createElement("payment");

                Attr attr = this.document.createAttribute("payment_id");
                attr.setValue(Integer.toString(entity.getId()));
                skill.setAttributeNode(attr);

                payments.appendChild(skill);
            });
        }

        return payments;
    }

    private Element marshallFriendSkills(List<SkillEntity> skillEntities) {
        Element skills = this.document.createElement("skills");

        if (skillEntities != null) {
            skillEntities.forEach(entity ->
            {
                Element skill = this.document.createElement("skill");

                Attr attr = this.document.createAttribute("skill_id");
                attr.setValue(Integer.toString(entity.getId()));
                skill.setAttributeNode(attr);

                skills.appendChild(skill);
            });
        }

        return skills;
    }

    public void save(File file) throws TransformerException, NullPointerException {
        if (this.document != null && file != null) {
            DOMSource source = new DOMSource(this.document);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } else throw new NullPointerException("Document or file is null");
    }

    public void unmarshall() {

        Element element = this.document.getDocumentElement();
        element.normalize();
        if ("persistence".equals(element.getNodeName()) && "who_gonna_help".equals(element.getAttribute("name"))) {

            NodeList children = element.getChildNodes();

            for (int index = 0; index < children.getLength(); index++) {
                Node child = children.item(index);
                switch (child.getNodeName()) {
                    case "payments":
                        unmarshallPayments(child);
                        break;
                    case "skills":
                        unmarshallSkills(child);
                        break;
                    case "friends":
                        unmarshallFriends(child);
                        break;
                }
            }
        }
    }

    private void unmarshallPayments(Node paymentNode) {
        NodeList paymentNodes = paymentNode.getChildNodes();
        if (paymentNodes != null) {
            for (int index = 0; index < paymentNodes.getLength(); index++) {
                Node payment = paymentNodes.item(index);

                if ("payment".equals(payment.getNodeName())) {
                    NamedNodeMap attributes = payment.getAttributes();
                    String idString = attributes.getNamedItem("payment_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);
                        PaymentEntity paymentEntity = new PaymentEntity();
                        paymentEntity.setId(id);
                        paymentEntity.setName(attributes.getNamedItem("name").getTextContent());

                        this.paymentEntityMap.put(id, paymentEntity);
                    }
                }
            }
        }
    }

    private void unmarshallSkills(Node skillNode) {
        NodeList skillNodes = skillNode.getChildNodes();
        if (skillNodes != null) {
            for (int index = 0; index < skillNodes.getLength(); index++) {
                Node skill = skillNodes.item(index);

                if ("skill".equals(skill.getNodeName())) {
                    NamedNodeMap attributes = skill.getAttributes();
                    String idString = attributes.getNamedItem("skill_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);
                        SkillEntity skillEntity = new SkillEntity();
                        skillEntity.setId(id);
                        skillEntity.setName(attributes.getNamedItem("name").getTextContent());

                        this.skillEntityMap.put(id, skillEntity);
                    }
                }
            }
        }
    }

    private void unmarshallFriends(Node friendNode) {
        NodeList friendNodes = friendNode.getChildNodes();
        if (friendNodes != null) {
            for (int index = 0; index < friendNodes.getLength(); index++) {
                Node friend = friendNodes.item(index);

                if ("friend".equals(friend.getNodeName())) {
                    NamedNodeMap attributes = friend.getAttributes();
                    String idString = attributes.getNamedItem("friend_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);
                        FriendEntity friendEntity = new FriendEntity();
                        friendEntity.setId(id);
                        friendEntity.setName(attributes.getNamedItem("name").getTextContent());
                        friendEntity.setContact(attributes.getNamedItem("contact").getTextContent());
                        friendEntity.setNote(attributes.getNamedItem("note").getTextContent());

                        NodeList friendChildren = friend.getChildNodes();
                        for (int childIndex = 0; childIndex < friendChildren.getLength(); childIndex++) {
                            Node friendChild = friendChildren.item(childIndex);
                            switch (friendChild.getNodeName()) {
                                case "payments":
                                    friendEntity.getPayments().addAll(unmarshallFriendPayments(friendChild.getChildNodes()));
                                    break;
                                case "skills":
                                    friendEntity.getSkills().addAll(unmarshallFriendSkills(friendChild.getChildNodes()));
                                    break;
                            }
                        }

                        this.friendEntityMap.put(id, friendEntity);
                    }
                }
            }
        }
    }

    private List<PaymentEntity> unmarshallFriendPayments(NodeList paymentNodes) {

        List<PaymentEntity> paymentEntities = new ArrayList<PaymentEntity>();

        if (paymentNodes != null) {
            for (int index = 0; index < paymentNodes.getLength(); index++) {
                Node skill = paymentNodes.item(index);

                if ("payment".equals(skill.getNodeName())) {
                    NamedNodeMap attributes = skill.getAttributes();
                    String idString = attributes.getNamedItem("payment_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);

                        PaymentEntity paymentEntity = this.paymentEntityMap.get(id);
                        if (paymentEntity != null) {
                            paymentEntities.add(paymentEntity);
                        }
                    }
                }
            }
        }

        return paymentEntities;
    }

    private List<SkillEntity> unmarshallFriendSkills(NodeList skillNodes) {

        List<SkillEntity> skillEntities = new ArrayList<SkillEntity>();

        if (skillNodes != null) {
            for (int index = 0; index < skillNodes.getLength(); index++) {
                Node skill = skillNodes.item(index);

                if ("skill".equals(skill.getNodeName())) {
                    NamedNodeMap attributes = skill.getAttributes();
                    String idString = attributes.getNamedItem("skill_id").getTextContent();
                    if (idString.matches("\\d+")) {
                        int id = Integer.parseInt(idString);

                        SkillEntity skillEntity = this.skillEntityMap.get(id);
                        if (skillEntity != null) {
                            skillEntities.add(skillEntity);
                        }
                    }
                }
            }
        }

        return skillEntities;
    }

    public void load(File file) throws IOException, SAXException, NullPointerException {
        if (file != null) {
            try {
                this.document = documentBuilder.parse(file);
                this.document.normalize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else throw new NullPointerException("File is null");
    }
}