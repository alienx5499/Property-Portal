# Property Portal Database Schema Assignment

## Business Context & Goal
A property portal where agents list real estate properties and potential buyers submit inquiries. The system manages property details, listing status, and inquiry tracking.

## Business Requirements
- Agents register with contact info and agency affiliations.
- Agents list properties with titles, descriptions, addresses, and listing dates.
- Properties have statuses (available, under_offer, sold).
- Properties may have multiple features (e.g., pool, garage).
- Buyers register with contact details and can submit inquiries per property.
- Inquiries include messages, dates, and statuses (new, responded, closed).
- Full-text search on property descriptions is supported.
- Inquiry response time metrics are tracked per agent.

## Frequent Queries
- Active listings by neighborhood and property type.
- Agent performance by closed deals.
- Average time on market per listing.
- Price trend analysis for a region.
- Inquiries per property last month.
- Offers pending acceptance.

## Deliverables
- An E-R diagram meeting the above requirements
- A brief justification of your design choices (normalization level, indexing, etc.) 